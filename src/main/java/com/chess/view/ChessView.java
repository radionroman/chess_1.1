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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.chess.model.RenderState;

public class ChessView extends JPanel{
    private JPanel boardPanel;
    private JPanel controlPanel;
    private JPanel chessPanel = new JPanel();
    private final HashMap<String, JButton> controlButtons = new HashMap<>();
    private final JButton[][] squares = new JButton[8][8];
    private final Color lightSquare = new Color(240, 240, 220); // beige
    private final Color darkSquare = new Color(118, 150, 86);   // green
    private final Color hLightSquare     = new Color(250, 255, 200); // highlighted beige (slightly greenish tint)
    private final Color hDarkSquare      = new Color(170, 180, 100); // highlighted green (brighter/warmer green)
    private JPanel menuPanel = new JPanel();

    public ChessView() {
       
        initBoard();
        initControls();
        chessPanel.add(controlPanel, BorderLayout.NORTH);
        chessPanel.add(boardPanel, BorderLayout.CENTER);
        add(chessPanel, "chessPanel");
        // add(menuPanel, "menuPanel");
        chessPanel.setPreferredSize(new Dimension(800,800));
        chessPanel.setBackground(new Color(150,75,0));
        controlPanel.setBackground(new Color(150,75,0));
        // cardLayout.show(mainPanel, "menuPanel");
        // add(mainPanel);
        
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
                squares[i][j] = button;
                button.setPreferredSize(new Dimension(80,80)); 

                boardPanel.add(button);
            }
        }
    }

    private void initControls(){
        controlPanel = new JPanel();

        JButton undoButton = new JButton();
        undoButton.setFont(new Font("SansSerif", Font.BOLD, 36));
        undoButton.setText("Undo");
        JButton menuButton = new JButton();
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 36));
        menuButton.setText("Menu");
        controlButtons.put("Undo", undoButton);
        controlPanel.add(undoButton);
        // controlPanel.add(menuButton);
        menuButton.addActionListener(e -> {

        });
        controlPanel.setPreferredSize(new Dimension(800, 70));

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

    public void showPromotionDialog(Consumer<String> callback){
            Object[] options = { "Queen", "Rook", "Bishop", "Knight" };
    String choice = (String) javax.swing.JOptionPane.showInputDialog(
            this,
            "Choose promotion piece:",
            "Pawn Promotion",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            "Queen");

    if (choice != null) {
        callback.accept(choice);
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
