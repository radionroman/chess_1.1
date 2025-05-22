package com.chess.view;
import java.awt.BorderLayout;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.chess.model.BoardSnapshot;


public class GamePanel extends JPanel{
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final BotProgressPanel botProgressPanel;
    private final MoveHistoryPanel moveHistoryPanel;


    public GamePanel() {
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        controlPanel = new ControlPanel();
        botProgressPanel = new BotProgressPanel();
        moveHistoryPanel = new MoveHistoryPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(botProgressPanel, BorderLayout.SOUTH);
        add(moveHistoryPanel, BorderLayout.EAST);
        


    }



    public void removeProgress(){
        if(botProgressPanel != null)botProgressPanel.remove();
    }





    public void mate(String color){
        JLabel label = new JLabel();
        label.setText(color + " lost!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        controlPanel.add(label);
    }

    public JProgressBar initBotProgress(){
        return botProgressPanel.initBotProgress();
    }


    public void setSquareListener(BiConsumer<Integer, Integer> listener){
         boardPanel.setSquareListener(listener);
    }

    public void refresh(BoardSnapshot boardState){
        boardPanel.refresh(boardState);
    }
    public void showPromotionDialog(Consumer<String> callback){
        boardPanel.showPromotionDialog(callback);
    }

    public void setControlListener(Consumer<String> listener){
        controlPanel.setControlListener(listener);
    }


}
