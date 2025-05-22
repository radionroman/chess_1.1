package com.chess.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
    private final JButton PVPButton, PVEWhiteButton, PVEBlackButton;
    public MenuPanel() {
        PVPButton = Style.MenuButton.create("PVP");
        PVEWhiteButton = Style.MenuButton.create("PVE White");
        PVEBlackButton = Style.MenuButton.create("PVE Black");

        setBackground(new Color(150,75,0));
        setOpaque(true);
        // Add vertical glue to center buttons vertically
        add(Box.createVerticalGlue());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton[] buttons = { PVPButton, PVEWhiteButton, PVEBlackButton };
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(300, 150));
            button.setMaximumSize(new Dimension(300, 150));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("SansSerif", Font.BOLD, 32));
            button.setFocusable(false);
            StyleSettings.applyControlButtonStyle(button);
            add(button);
            add(Box.createVerticalStrut(20)); // spacing between buttons
        }

        // Add vertical glue again to push content to center
        add(Box.createVerticalGlue());
        StyleSettings.addChangeListener((PropertyChangeEvent evt) -> {
            switch (evt.getPropertyName()) {
                case "BOARD_BG" -> setBackground(StyleSettings.get(StyleSettings.BoardStyle.BOARD_BG));
                case "CONTROL_FONT", "CONTROL_FG", "CONTROL_BUTTON_BG" -> {
                    StyleSettings.applyControlButtonStyle(PVPButton);
                    StyleSettings.applyControlButtonStyle(PVEWhiteButton);
                    StyleSettings.applyControlButtonStyle(PVEBlackButton);
                }

            }
            repaint();
        });
        revalidate();
        repaint();
    }


    public void setMenuListener(Consumer<String> callback) {
        PVPButton.addActionListener(e -> {
                callback.accept("PVP");
                ScreenManager.showScreen(Screens.GAMEBOARD);
        });
        PVEWhiteButton.addActionListener(e -> {
            callback.accept("PVEWhite");
            ScreenManager.showScreen(Screens.GAMEBOARD);
        });
        PVEBlackButton.addActionListener(e -> {
            callback.accept("PVEBlack");
            ScreenManager.showScreen(Screens.GAMEBOARD);
        });
    }
}
