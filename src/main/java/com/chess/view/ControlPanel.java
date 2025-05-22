package com.chess.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    private final JButton undoButton;
    private final JButton menuButton;
    private final JButton styleToggleButton;

    public ControlPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create buttons and apply the centralized style
        menuButton = new JButton("Menu");
        undoButton = new JButton("Undo");
        styleToggleButton = new JButton("ToggleStyle");
        StyleSettings.applyControlButtonStyle(menuButton);
        StyleSettings.applyControlButtonStyle(undoButton);
        StyleSettings.applyControlButtonStyle(styleToggleButton);

        // Place them
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(menuButton, gbc);

        gbc.gridx = 3;
        add(undoButton, gbc);
        gbc.gridx = 6;
        add(styleToggleButton, gbc);

        setPreferredSize(new Dimension(800, 70));
        setBackground(StyleSettings.get(StyleSettings.BUTTON_FONT.BG));

        // Listen for style changes and re‐apply as needed
        StyleSettings.addChangeListener((PropertyChangeEvent evt) -> {
            switch (evt.getPropertyName()) {
                case "CONTROL_BG" -> setBackground(StyleSettings.get(StyleSettings.BUTTON_FONT.BG));
                case "CONTROL_FONT", "CONTROL_FG", "CONTROL_BUTTON_BG" -> {
                    StyleSettings.applyControlButtonStyle(menuButton);
                    StyleSettings.applyControlButtonStyle(undoButton);
                    StyleSettings.applyControlButtonStyle(styleToggleButton);
                }

            }
            repaint();
        });
    }

    /**
     * Register a listener for both buttons.
     * The listener receives the button's text ("Menu" or "Undo").
     */
    public void setControlListener(Consumer<String> listener) {
        for (Component c : getComponents()) {
            if (c instanceof JButton btn) {
                // remove old
                if (btn.getActionListeners().length > 0) {
                    btn.removeActionListener(btn.getActionListeners()[0]);
                }
                btn.addActionListener(e -> listener.accept(btn.getText()));
            }
        }
    }
}
