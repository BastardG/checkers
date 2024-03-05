package ru.bastard.checkers.game;

import ru.bastard.checkers.game.pieces.Checker;
import ru.bastard.checkers.game.pieces.CheckerColor;
import ru.bastard.checkers.game.pieces.King;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel {

    public static final int TILE_SIZE = 50;

    private static final int columns = 8;
    private static final int rows = 8;

    private ArrayList<Checker> checkers = new ArrayList<>();

    private Checker selectedChecker;

    private Input input = new Input(this);

    public Board() {
        this.setPreferredSize(new Dimension(columns * TILE_SIZE, rows * TILE_SIZE));
        this.setBackground(Color.GREEN);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
    }

    public void addPieces() {
        checkers.add(new Checker(this, 1, 0, CheckerColor.BLACK));
        checkers.add(new Checker(this, 3, 0, CheckerColor.BLACK));
        checkers.add(new Checker(this, 5, 0, CheckerColor.BLACK));
        checkers.add(new Checker(this, 7, 0, CheckerColor.BLACK));

        checkers.add(new Checker(this, 0, 1, CheckerColor.BLACK));
        checkers.add(new Checker(this, 2, 1, CheckerColor.BLACK));
        checkers.add(new Checker(this, 4, 1, CheckerColor.BLACK));
        checkers.add(new Checker(this, 6, 1, CheckerColor.BLACK));

        checkers.add(new Checker(this, 1, 2, CheckerColor.BLACK));
        checkers.add(new Checker(this, 3, 2, CheckerColor.BLACK));
        checkers.add(new Checker(this, 5, 2, CheckerColor.BLACK));
        checkers.add(new Checker(this, 7, 2, CheckerColor.BLACK));

        checkers.add(new Checker(this, 0, 7, CheckerColor.WHITE));
        checkers.add(new Checker(this, 2, 7, CheckerColor.WHITE));
        checkers.add(new Checker(this, 4, 7, CheckerColor.WHITE));
        checkers.add(new Checker(this, 6, 7, CheckerColor.WHITE));

        checkers.add(new Checker(this, 1, 6, CheckerColor.WHITE));
        checkers.add(new Checker(this, 3, 6, CheckerColor.WHITE));
        checkers.add(new Checker(this, 5, 6, CheckerColor.WHITE));
        checkers.add(new Checker(this, 7, 6, CheckerColor.WHITE));

        checkers.add(new Checker(this, 0, 5, CheckerColor.WHITE));
        checkers.add(new Checker(this, 2, 5, CheckerColor.WHITE));
        checkers.add(new Checker(this, 4, 5, CheckerColor.WHITE));
        checkers.add(new Checker(this, 6, 5, CheckerColor.WHITE));

    }

    public Checker getChecker(int col, int row) {
        for (Checker c : checkers) {
            if (c.getCol() == col && c.getRow() == row) {
                return c;
            }
        }
        return null;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if ((c+r) % 2 == 0) {
                    g2d.setColor(new Color(222, 208, 156));
                } else {
                    g2d.setColor(new Color(114, 140, 66));
                }
                g2d.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        for (Checker checker : checkers) {
            checker.paint(g2d);
        }

    }

    public Checker getSelectedChecker() {
        return selectedChecker;
    }

    public void setSelectedChecker(Checker selectedChecker) {
        this.selectedChecker = selectedChecker;
    }

    public boolean isValidMove(Move move) {
        if (!nobodyInCell(move)) {
            return false;
        }
        if (isMovingInWhiteCell(move)) {
            return false;
        }
        if (sameTeam(move.getChecker(), move.getCapture())) {
            return false;
        }
        if (moveMoreThanOneCellAndNotCapture(move)) {
            return false;
        }
        if (move.getCapture() != null && tryToCaptureMoreThenOne(move)) {
            return false;
        }
        return moveBackwards(move);
    }

    private boolean tryToCaptureMoreThenOne(Move move) {
        if (move.getChecker() instanceof King) {
            int counter = 0;
            switch (move.getDirection()) {
                case UP_LEFT -> {
                    for (int c = move.getOldCol() - 1, r = move.getOldRow() - 1;
                         c > move.getCapture().getCol(); c--, r--) {
                        if (getChecker(c, r) != null) {
                            counter++;
                        }
                    }
                    return counter > 1;
                }
                case UP_RIGHT -> {
                    for (int c = move.getOldCol() + 1, r = move.getOldRow() - 1;
                         c < move.getCapture().getCol(); c++, r--) {
                        if (getChecker(c, r) != null) {
                            counter++;
                        }
                    }
                    return counter > 1;
                }
                case DOWN_LEFT -> {
                    for (int c = move.getOldCol() - 1, r = move.getOldRow() + 1;
                         c > move.getCapture().getCol(); c--, r++) {
                        if (getChecker(c, r) != null) {
                            counter++;
                        }
                    }
                    return counter > 1;
                }
                case DOWN_RIGHT -> {
                    for (int c = move.getOldCol(), r = move.getOldRow();
                         c < move.getCapture().getCol(); c++, r++) {
                        if (getChecker(c, r) != null) {
                            counter++;
                        }
                    }
                    return counter > 1;
                }
            }
        }
        return false;
    }

    private boolean moveMoreThanOneCellAndNotCapture(Move move) {
        var checker = move.getChecker();
        var capture = move.getCapture();
        var oldCol = move.getOldCol();
        var newCol = move.getNewCol();
        if (checker instanceof King)
            return false;
        if (capture == null) {
            return ((newCol > (oldCol + 1) || newCol < (oldCol - 1)));
        } else {
            if (oldCol > newCol) {
                return !(capture.getCol() - 1 == newCol);
            }
            if (oldCol < newCol) {
                return !(capture.getCol() + 1 == newCol);
            }
        }
        return false;
    }

    private boolean isMovingInWhiteCell(Move move) {
        return (move.getNewCol() + move.getNewRow()) % 2 == 0;
    }

    private boolean nobodyInCell(Move move) {
        Checker checker = null;
        for (Checker c : checkers) {
            if (move.getNewRow() == c.getRow() && move.getNewCol() == c.getCol())
                checker = c;
        }
        return checker == null;
    }

    private boolean moveBackwards(Move move) {
        if (move.getChecker() instanceof King){
            System.out.println("King true");
            return true;
        }
        if (move.getCapture() != null) return true;
        switch (move.getChecker().getColor()) {
            case BLACK -> {
                if (move.getOldRow() >= move.getNewRow()) return false;
            }
            case WHITE -> {
                if (move.getOldRow() <= move.getNewRow()) return false;
            }
        }
        return true;
    }

    private boolean sameTeam(Checker c1, Checker c2) {
        if (c1 == null || c2 == null)
            return false;

        return c1.getColor() == c2.getColor();
    }

    public void makeMove(Move move) {
        move.getChecker().setCol(move.getNewCol());
        move.getChecker().setRow(move.getNewRow());
        move.getChecker().setXPos(move.getNewCol() * TILE_SIZE);
        move.getChecker().setYPos(move.getNewRow() * TILE_SIZE);

        capture(move);
        if(canCreateQueen(move)) {
            createQueen(move);
        }
        if (checkers.stream().noneMatch(c -> c.getColor() == CheckerColor.BLACK)) {
            System.out.println("White WIN!");
            System.exit(1);
        }
        if (checkers.stream().noneMatch(c -> c.getColor() == CheckerColor.WHITE)) {
            System.out.println("Black WIN!");
            System.exit(1);
        }
    }

    private void createQueen(Move move) {
        var row = move.getNewRow();
        var col = move.getNewCol();
        var color = move.getChecker().getColor();
        checkers.remove(move.getChecker());
        checkers.add(new King(this, col, row, color));
    }

    private boolean canCreateQueen(Move move) {
        switch (move.getChecker().getColor()) {
            case BLACK -> {
                return move.getChecker().getRow() == 7;
            }
            case WHITE -> {
                return move.getChecker().getRow() == 0;
            }
        }
        return false;
    }

    public void capture(Move move) {
        checkers.remove(move.getCapture());
    }
    
}
