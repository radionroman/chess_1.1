package com.chess.view;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class ScreenManager {
    private static final CardLayout layout = new CardLayout();
    private static JPanel panel;

    public static CardLayout getLayout() {
        return layout;
    }

    public static void setPanel(JPanel panel) {
        ScreenManager.panel = panel;
    }

    public static void showScreen(Screens screen) {
        layout.show(panel, screen.toString());
    }
}
