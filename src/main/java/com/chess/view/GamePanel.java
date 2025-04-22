package com.chess.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chess.model.RenderState;


public class GamePanel extends JPanel{
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final GameRightSidebarPanel rightSidebarPanel;


    private JPanel botProgressBar;

    public GamePanel() {
        setLayout(new BorderLayout());
        boardPanel = new BoardPanel();
        controlPanel = new ControlPanel();
        rightSidebarPanel = new GameRightSidebarPanel();
        
        add(rightSidebarPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        boardPanel.setBackground(new Color(150,75,0));
        boardPanel.setOpaque(true);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        
    }






    public void initBotProgress() {
        botProgressBar = new JPanel();
        
        add(botProgressBar);
    }

    public void mate(String color){
        JLabel label = new JLabel();
        label.setText(color + " lost!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        controlPanel.add(label);
    }




    public void setBoardClickedListeners(BiConsumer<Integer, Integer> listener){
         boardPanel.setBoardClickedListeners(listener);
    }
    public void refresh(RenderState boardState){
        boardPanel.refresh(boardState);
    }
    public void showPromotionDialog(Consumer<String> callback){
        boardPanel.showPromotionDialog(callback);
    }

    public void setControlClickedListener(Consumer<String> listener){
        controlPanel.setControlClickedListener(listener);
    }


}
