package ru.bastard.checkers.game;

import ru.bastard.checkers.game.Board;
import ru.bastard.checkers.game.Move;
import ru.bastard.checkers.game.pieces.Checker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    private Board board;

    public Input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        Checker checker = board.getChecker(col, row);
        if (checker != null) {
            board.setSelectedChecker(checker);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.getSelectedChecker() != null) {
            board.getSelectedChecker().setXPos(e.getX() - Board.TILE_SIZE / 2);
            board.getSelectedChecker().setYPos(e.getY() - Board.TILE_SIZE / 2);

            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        if (board.getSelectedChecker() != null) {
            Move move = new Move(board, board.getSelectedChecker(), col, row);

            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.getSelectedChecker().setXPos(board.getSelectedChecker().getCol() * Board.TILE_SIZE);
                board.getSelectedChecker().setYPos(board.getSelectedChecker().getRow() * Board.TILE_SIZE);
            }
        }

        board.setSelectedChecker(null);
        board.repaint();
    }

}
