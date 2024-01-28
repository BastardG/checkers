package ru.bastard.checkers;

import ru.bastard.checkers.game.Board;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(new Color(27, 87, 14));
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);

        Board board = new Board();

        frame.add(board);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setVisible(true);
    }

}