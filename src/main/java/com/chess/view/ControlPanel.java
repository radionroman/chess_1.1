package com.chess.view;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import static com.chess.view.Style.CONTROL_BUTTONS_FONT;

public class ControlPanel extends JPanel{
    
    private final HashMap<String, JButton> controlButtons = new HashMap<>();

    public ControlPanel(){
        JButton undoButton = new JButton();
        undoButton.setFont(CONTROL_BUTTONS_FONT);
        undoButton.setText("Undo");
        JButton menuButton = new JButton();
        menuButton.setFont(CONTROL_BUTTONS_FONT);
        menuButton.setText("Menu");
        controlButtons.put("Undo", undoButton);
        add(undoButton);
        add(menuButton);
        menuButton.addActionListener(e -> {
            ScreenManager.showScreen(Screens.MENU);
        });
        setPreferredSize(new Dimension(800, 70));

    }
    public void setControlClickedListener(Consumer<String> listener){
        for (String key : controlButtons.keySet()){
            if (controlButtons.get(key).getActionListeners().length != 0) controlButtons.get(key).removeActionListener(controlButtons.get(key).getActionListeners()[0]);
            controlButtons.get(key).addActionListener(e -> listener.accept(key));
        }
    }
}
