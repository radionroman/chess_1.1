package com.chess.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class BotProgressPanel extends JPanel{

    
    JProgressBar progressBar = new JProgressBar(0, 100);

    public BotProgressPanel() {
        progressBar = new JProgressBar(0, 100);
    }
    public JProgressBar initBotProgress() {

        setVisible(true);
        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(0, 50));
        StyleSettings.applyProgressBarStyle(progressBar);
        add(progressBar);
        return progressBar;
    }

    public void remove() {
        setVisible(false);
        remove(progressBar);
        revalidate();
        repaint();
    }


}
