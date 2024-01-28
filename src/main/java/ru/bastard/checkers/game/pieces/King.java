package ru.bastard.checkers.game.pieces;

import ru.bastard.checkers.game.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class King extends Checker {

    public King(Board board, int col, int row, CheckerColor color) {
        super(board, col, row, color);
        try {
            switch (color) {
                case BLACK -> this.sprite =
                        ImageIO.read(ClassLoader.getSystemResourceAsStream("black_queen.png"))
                                .getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
                case WHITE -> this.sprite =
                        ImageIO.read(ClassLoader.getSystemResourceAsStream("white_queen.png"))
                                .getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            System.exit(-1);
        }
    }


}
