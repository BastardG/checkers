package ru.bastard.checkers.game.pieces;

import ru.bastard.checkers.game.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Checker {

    protected int col, row;
    protected int xPos, yPos;

    protected CheckerColor color;
    protected int value;

    protected Image sprite;
    protected Board board;

    public Checker(Board board, int col, int row, CheckerColor color) {
        this.board = board;
        this.col = col;
        this.row = row;

        this.xPos = col * board.TILE_SIZE;
        this.yPos = row * board.TILE_SIZE;

        this.color = color;
        try {
            switch (color) {
                case BLACK -> this.sprite =
                        ImageIO.read(ClassLoader.getSystemResourceAsStream("black_checker.png"))
                                .getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
                case WHITE -> this.sprite =
                        ImageIO.read(ClassLoader.getSystemResourceAsStream("white_checker.png"))
                                .getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }

    public CheckerColor getColor() {
        return color;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public String toString() {
        return "Checker{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }
}
