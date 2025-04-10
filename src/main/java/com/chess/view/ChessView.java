package com.chess.view;
import java.awt.*;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.chess.model.RenderState;

public class ChessView extends JFrame{
    private JPanel boardPanel;
    private JPanel controlPanel;
    private HashMap<String, JButton> controlButtons = new HashMap<>();
    private JButton[][] squares = new JButton[8][8];
    private final Color lightSquare = new Color(240, 240, 220); // beige
    private final Color darkSquare = new Color(118, 150, 86);   // green
    private final Color hLightSquare     = new Color(250, 255, 200); // highlighted beige (slightly greenish tint)
    private final Color hDarkSquare      = new Color(170, 180, 100); // highlighted green (brighter/warmer green)
    


    public ChessView() {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(new Font("SansSerif", Font.BOLD, 36)); // Big for Unicode pieces
                button.setBackground((i + j) % 2 == 0 ? lightSquare : darkSquare);
                button.setForeground(Color.BLACK);
                button.setOpaque(true);
                squares[i][j] = button;
                boardPanel.add(button);
            }
        }
        controlPanel = new JPanel();
        JButton button = new JButton();
        button.setFont(new Font("SansSerif", Font.BOLD, 36));
        button.setText("Undo");
        controlButtons.put("Undo", button);
        controlPanel.add(button);

        add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(boardPanel, BorderLayout.CENTER);

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

    public void setControlClickedListener(Consumer<String> listener){
        for (String key : controlButtons.keySet()){
            controlButtons.get(key).addActionListener(e -> listener.accept(key));
        }
    }

    public void refresh(RenderState boardState){
        String[][] board = boardState.getBoard();
        boolean[][] isActiveSquares = boardState.getSquaresActive();
        int[][] lastMove = boardState.getLastMove();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                squares[i][j].setText(board[i][j]);
                squares[i][j].setEnabled(isActiveSquares[i][j]);
                squares[i][j].setBorder(BorderFactory.createEmptyBorder());
                if(isActiveSquares[i][j])squares[i][j].setBackground((i + j) % 2 == 0 ? hLightSquare : hDarkSquare);
                else squares[i][j].setBackground((i + j) % 2 == 0 ? lightSquare : darkSquare);
            }
        }

        if (lastMove != null) {
            squares[lastMove[0][0]][lastMove[0][1]].setBorder(new LineBorder(Color.RED, 4));
            squares[lastMove[1][0]][lastMove[1][1]].setBorder(new LineBorder(Color.RED, 4));
        }
        
    }
    

}
