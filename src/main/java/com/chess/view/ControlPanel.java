package com.chess.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

    public ControlPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton undoButton = Style.ControlButton.create("Undo");
        JButton menuButton = Style.ControlButton.create("Menu");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;
        add(menuButton, gbc);
        gbc.gridx = 3;
        gbc.gridy =0;
        add(undoButton, gbc);
       

        setPreferredSize(new Dimension(800, 70));
        setBackground(Color.BLACK);

    }
    public void setControlClickedListener(Consumer<String> listener){
        Component[] controlButtons = getComponents();
        for (Component comp : controlButtons){
            if (comp instanceof JButton button) {
                if (button.getActionListeners().length != 0) button.removeActionListener(button.getActionListeners()[0]);
                    button.addActionListener(e -> listener.accept(button.getText()));}
        }
    }
}
