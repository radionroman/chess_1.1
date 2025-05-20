package com.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.chess.model.BoardSnapshot;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public class BoardPanel extends JPanel {
    private final JButton[][] squares = new JButton[BOARD_ROWS][BOARD_COLS];
    private final JPanel boardPanel;

    public BoardPanel() {
        boardPanel = new JPanel(new GridLayout(BOARD_ROWS, BOARD_COLS));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // build buttons
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(StyleSettings.get(StyleSettings.Key.PIECE_FONT));
                button.setEnabled(false);
                button.setForeground(Color.BLACK);
                button.setOpaque(true);

                squares[i][j] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel);
        boardPanel.setPreferredSize(new Dimension(640, 640));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(getWidth(), getHeight());
                size = Math.max(size, 560);
                boardPanel.setPreferredSize(new Dimension((int) (size * 0.9), (int) (size * 0.9)));
                boardPanel.revalidate();
            }
        });

        // if styles change at runtime, repaint the board backgrounds
        StyleSettings.addChangeListener((PropertyChangeEvent evt) -> {
            // only care about square-color changes or font changes
            switch (evt.getPropertyName()) {
                case "EVEN_SQUARE", "ODD_SQUARE", "EVEN_HIGHLIGHT", "ODD_HIGHLIGHT", "PIECE_FONT" -> repaint();
            }
        });
    }

    /** Register clicks on the squares. */
    public void setSquareListener(BiConsumer<Integer, Integer> listener) {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                final int r = i, c = j;
                JButton btn = squares[i][j];
                // remove old
                if (btn.getActionListeners().length > 0) {
                    btn.removeActionListener(btn.getActionListeners()[0]);
                }
                btn.addActionListener(e -> listener.accept(r, c));
            }
        }
    }

    /** Show pawn-promotion choices. */
    public void showPromotionDialog(Consumer<String> callback) {
        Object[] options = { "Queen", "Rook", "Bishop", "Knight" };
        String choice = (String) JOptionPane.showInputDialog(
            this,
            "Choose promotion piece:",
            "Pawn Promotion",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            "Queen"
        );
        if (choice != null) {
            callback.accept(choice);
        }
    }

    /** Update all piece texts and square colors. */
    public void refresh(BoardSnapshot boardSnapshot) {
        String[][] board = boardSnapshot.getBoard();
        boolean[][] active = boardSnapshot.getSquaresActive();
        int[][] lastMove = boardSnapshot.getLastMove();

        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                JButton btn = squares[i][j];
                btn.setText(board[i][j]);
                btn.setEnabled(true);
                btn.setBorder(BorderFactory.createEmptyBorder());
                // piece font may have changed
                btn.setFont(StyleSettings.get(StyleSettings.Key.PIECE_FONT));

                boolean isEven = ((i + j) % 2 == 0);
                if (active[i][j]) {
                    // highlight
                    Color highlight = isEven
                        ? StyleSettings.get(StyleSettings.Key.EVEN_HIGHLIGHT)
                        : StyleSettings.get(StyleSettings.Key.ODD_HIGHLIGHT);
                    btn.setBackground(highlight);
                } else {
                    // normal square
                    Color sq = isEven
                        ? StyleSettings.get(StyleSettings.Key.EVEN_SQUARE)
                        : StyleSettings.get(StyleSettings.Key.ODD_SQUARE);
                    btn.setBackground(sq);
                }
            }
        }

        // draw last-move borders
        if (lastMove != null) {
            squares[lastMove[0][0]][lastMove[0][1]]
                .setBorder(new LineBorder(Color.RED, 4));
            squares[lastMove[1][0]][lastMove[1][1]]
                .setBorder(new LineBorder(Color.RED, 4));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int size = Math.min(d.width, d.height);
        return new Dimension(size, size);
    }
}
