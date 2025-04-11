package com.chess.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.chess.model.RenderState;

public class ChessView extends JFrame{
    private JPanel boardPanel;
    private JPanel controlPanel;
    private final HashMap<String, JButton> controlButtons = new HashMap<>();
    private final JButton[][] squares = new JButton[8][8];
    private final Color lightSquare = new Color(240, 240, 220); // beige
    private final Color darkSquare = new Color(118, 150, 86);   // green
    private final Color hLightSquare     = new Color(250, 255, 200); // highlighted beige (slightly greenish tint)
    private final Color hDarkSquare      = new Color(170, 180, 100); // highlighted green (brighter/warmer green)


    public ChessView() {
        initWindow();
        initBoard();
        initControls();
        add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(boardPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private void initWindow(){
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
    }

    private void initBoard(){

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // boardPanel.setPreferredSize(new Dimension(800,800));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(new Font("SansSerif", Font.BOLD, 52)); // Big for Unicode pieces
                button.setEnabled(false);
                button.setForeground(Color.BLACK);
                button.setBackground((i + j) % 2 == 0 ? lightSquare : darkSquare);
                button.setForeground(Color.BLACK);
                button.setOpaque(true);
                button.setBounds(i*100, j*100, 100, 100);
                squares[i][j] = button;
                button.setPreferredSize(new Dimension(100,100)); 

                boardPanel.add(button);
            }
        }
    }

    private void initControls(){
        controlPanel = new JPanel();

        JButton undoButton = new JButton();
        undoButton.setFont(new Font("SansSerif", Font.BOLD, 36));
        undoButton.setText("Undo");

        JButton pvpButton = new JButton();
        pvpButton.setFont(new Font("SansSerif", Font.BOLD, 36));
        pvpButton.setText("PVP");

        controlButtons.put("Undo", undoButton);
        controlButtons.put("PVP", pvpButton);

        controlPanel.add(undoButton);
        controlPanel.add(pvpButton);
        controlPanel.setPreferredSize(new Dimension(800, 100));

    };

    public void mate(String color){
        JLabel label = new JLabel();
        label.setText(color + " lost!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        controlPanel.add(label);
    }


    // Listeners
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

    // Rendering
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
