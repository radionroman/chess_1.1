package com.chess.view;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import javax.swing.*;

import com.chess.model.BoardState;

public class ChessView extends JFrame{
    private JPanel panel;
    private JButton[][] squares = new JButton[8][8];

    public ChessView() {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                squares[i][j] = button;
                panel.add(button);
            }
        }
        add(panel, BorderLayout.CENTER);
        setSize(800,800);
        setVisible(true);
    }

    public void setBoardClickedListeners(BiConsumer<Integer, Integer> listener){
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                final int r = i;
                final int c = j;
                squares[i][j].addActionListener(e -> listener.accept(r, c));
            }
        }
    }

    public void refresh(BoardState boardState){
        String[][] board = boardState.getBoard();
        boolean[][] isActiveSquares = boardState.getSquaresActive();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                squares[i][j].setText(board[i][j]);
                squares[i][j].setEnabled(isActiveSquares[i][j]);
            }
        }
    }

}
