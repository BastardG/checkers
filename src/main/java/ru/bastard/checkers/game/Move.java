package ru.bastard.checkers.game;

import ru.bastard.checkers.game.pieces.Checker;
import ru.bastard.checkers.game.pieces.CheckerColor;

import static ru.bastard.checkers.game.Move.MoveDirection.*;

public class Move {

    private int oldCol;
    private int oldRow;
    private int newCol;
    private int newRow;

    private Checker checker;
    private Checker capture;

    private Board board;

    private MoveDirection direction;

    public Move(Board board, Checker checker, int newCol, int newRow) {
        this.board = board;
        this.oldCol = checker.getCol();
        this.oldRow = checker.getRow();
        this.newCol = newCol;
        this.newRow = newRow;

        this.checker = checker;

        if (newRow == (oldRow + 1) || newRow == (oldRow - 1)) {
            // Если попытались сходить вперед или назад на одну клетку
            capture = null;
            return;
        }

        if ((newRow > oldRow) && checker.getColor() == CheckerColor.BLACK ||
                (newRow < oldRow) && checker.getColor() == CheckerColor.WHITE) { // Если сходили вперёд
            switch (checker.getColor()) {
                case WHITE -> {
                    if (newCol > oldCol) {// Если сходили на право
                        capture = board.getChecker(newCol - 1, newRow + 1);
                        direction = UP_RIGHT;
                    } else { //Если сходили на лево
                        capture = board.getChecker(newCol + 1, newRow + 1);
                        direction = UP_LEFT;
                    }
                }
                case BLACK -> {
                    if (newCol > oldCol) {// Если сходили на право
                        capture = board.getChecker(newCol - 1, newRow - 1);
                        direction = DOWN_RIGHT;
                    } else { //Если сходили на лево
                        capture = board.getChecker(newCol + 1, newRow - 1);
                        direction = DOWN_LEFT;
                    }
                }
            }
        } else if ((newRow > oldRow) && checker.getColor() == CheckerColor.WHITE ||
                (newRow < oldRow) && checker.getColor() == CheckerColor.BLACK) { // Если сходили назад
            switch (checker.getColor()) {
                case BLACK -> {
                    if (newCol > oldCol) {// Если сходили на право
                        capture = board.getChecker(newCol - 1, newRow + 1);
                        direction = UP_RIGHT;
                    } else { //Если сходили на лево
                        capture = board.getChecker(newCol + 1, newRow + 1);
                        direction = UP_LEFT;
                    }
                }
                case WHITE -> {
                    if (newCol > oldCol) {// Если сходили на право
                        capture = board.getChecker(newCol - 1, newRow - 1);
                        direction = DOWN_RIGHT;
                    } else { //Если сходили на лево
                        capture = board.getChecker(newCol + 1, newRow - 1);
                        direction = DOWN_LEFT;
                    }
                }
            }
        }
    }

    public int getOldCol() {
        return oldCol;
    }

    public void setOldCol(int oldCol) {
        this.oldCol = oldCol;
    }

    public int getOldRow() {
        return oldRow;
    }

    public void setOldRow(int oldRow) {
        this.oldRow = oldRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public Checker getChecker() {
        return checker;
    }

    public Checker getCapture() {
        return capture;
    }

    enum MoveDirection {
        UP_RIGHT,
        UP_LEFT,
        DOWN_RIGHT,
        DOWN_LEFT
    }

    public MoveDirection getDirection() {
        return direction;
    }
}
