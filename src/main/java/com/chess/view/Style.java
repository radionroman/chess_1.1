package com.chess.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class Style {
    public static final Color CHESS_BEIGE = new Color(240, 240, 220); // beige
    public static final Color CHESS_GREEN = new Color(118, 150, 86);   // green
    public static final Color CHESS_BEIGE_HIGHLIGHT     = new Color(250, 255, 200); // highlighted beige (slightly greenish tint)
    public static final Color CHESS_GREEN_HIGHLIGHT      = new Color(170, 180, 100); // highlighted green (brighter/warmer green)

    public static final Font CONTROL_BUTTONS_FONT = new Font("SansSerif", Font.BOLD, 36);
    public static final Font CHESS_PIECE_FONT = new Font("SansSerif", Font.BOLD, 52);

    public static class MenuButton {
        public static JButton apply(JButton button) {
            button.setBackground(CHESS_BEIGE);
            button.setForeground(CHESS_GREEN);
            button.setFocusPainted(false);

            return button;
        } 

        public static JButton create(String text) {
            return apply(new JButton(text));
        }
    } 
    public static class ControlButton {
        public static JButton apply(JButton button) {
            button.setBackground(CHESS_BEIGE);
            button.setForeground(CHESS_GREEN);
            button.setFocusPainted(false);
            
            return button;
        } 

        public static JButton create(String text) {
            return apply(new JButton(text));
        }
    } 
}
