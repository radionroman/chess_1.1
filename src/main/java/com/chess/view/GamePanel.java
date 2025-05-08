package com.chess.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.chess.model.RenderState;


public class GamePanel extends JPanel{
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;

    private JPanel botProgressBar;

    public GamePanel() {
        setLayout(new BorderLayout());
        boardPanel = new BoardPanel();
        controlPanel = new ControlPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        boardPanel.setBackground(new Color(150,75,0));
        boardPanel.setOpaque(true);
        
    }






    public JProgressBar initBotProgress() {
        botProgressBar = new JPanel();
        botProgressBar.setLayout(new GridLayout(1,1));
        botProgressBar.setPreferredSize(new Dimension(0, 50));
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setForeground(Color.RED);
        bar.setBackground(Color.BLACK);
        bar.setStringPainted(true);
        bar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        botProgressBar.add(bar );
        add(botProgressBar, BorderLayout.SOUTH);
        return bar;
    }

    public void removeProgress(){
        if(botProgressBar != null)botProgressBar.setVisible(false);
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
