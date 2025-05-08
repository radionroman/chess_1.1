package com.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.chess.model.RenderState;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;
import static com.chess.view.Style.CHESS_BEIGE;
import static com.chess.view.Style.CHESS_BEIGE_HIGHLIGHT;
import static com.chess.view.Style.CHESS_GREEN;
import static com.chess.view.Style.CHESS_GREEN_HIGHLIGHT;
import static com.chess.view.Style.CHESS_PIECE_FONT;

public class BoardPanel extends JPanel{
    private final JButton[][] squares = new JButton[BOARD_ROWS][BOARD_COLS];
    private BiConsumer<Integer, Integer> activeListener;
    private final JPanel boardPanel;

    public BoardPanel() {
        
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(CHESS_PIECE_FONT); // Big for Unicode pieces
                button.setEnabled(false);
                button.setForeground(Color.BLACK);
                button.setBackground((i + j) % 2 == 0 ? CHESS_BEIGE : CHESS_GREEN);
                button.setOpaque(true);
                squares[i][j] = button;
                
                boardPanel.add(button);
                
            }
        }
        
        add(boardPanel);
        boardPanel.setPreferredSize(new Dimension(560,560));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(getWidth(), getHeight());
                size = Math.max(size, 560);
                boardPanel.setPreferredSize(new Dimension((int) Math.floor(size * 0.9), (int) Math.floor(size * 0.9)));
                boardPanel.revalidate();
            }
        });
    }

        // Listeners
    public void setBoardClickedListeners(BiConsumer<Integer, Integer> listener){
        this.activeListener = listener;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                final int r = i;
                final int c = j;
                if (squares[i][j].getActionListeners().length != 0) squares[i][j].removeActionListener(squares[i][j].getActionListeners()[0]);
                squares[i][j].addActionListener(e -> listener.accept(r, c));
            }
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
                squares[i][j].setEnabled(true);
                squares[i][j].setBorder(BorderFactory.createEmptyBorder());
                if(isActiveSquares[i][j])squares[i][j].setBackground((i + j) % 2 == 0 ? CHESS_BEIGE_HIGHLIGHT : CHESS_GREEN_HIGHLIGHT);
                else squares[i][j].setBackground((i + j) % 2 == 0 ? CHESS_BEIGE : CHESS_GREEN);
                
            }
        }
        

        if (lastMove != null) {
            squares[lastMove[0][0]][lastMove[0][1]].setBorder(new LineBorder(Color.RED, 4));
            squares[lastMove[1][0]][lastMove[1][1]].setBorder(new LineBorder(Color.RED, 4));
        }
        
    }
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int size = Math.min(d.width, d.height);
        return new Dimension(size, size);
    }



}
