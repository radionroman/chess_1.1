package com.chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class MoveHistoryPanel extends JPanel{

    private final JScrollPane moveHistoryScrollPane;
    private final JPanel moveHistoryList;
    private final JToolBar moveHistoryToolBar;

    public MoveHistoryPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 200));
        
        moveHistoryList = new JPanel();
        moveHistoryList.setLayout(new BoxLayout(moveHistoryList, BoxLayout.Y_AXIS));
        

        moveHistoryScrollPane = new JScrollPane(moveHistoryList); 
        moveHistoryScrollPane.setBackground(Color.BLACK);
        moveHistoryList.setBackground(Color.BLACK);
        moveHistoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        moveHistoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(moveHistoryScrollPane, BorderLayout.CENTER);

        moveHistoryToolBar = new JToolBar(JToolBar.HORIZONTAL);
        JButton plusButton = new JButton("+");
        JButton minusButton = new JButton("-");
        plusButton.addActionListener((e) -> {
            addMoveToHistory(new MoveHistoryEntryComponent(plusButton, minusButton, "Test"));
        });        
        minusButton.addActionListener((e) -> {
            removeMoveFromHistory();
        });
        
        moveHistoryToolBar.add(plusButton);
        moveHistoryToolBar.add(minusButton);
        moveHistoryToolBar.setFloatable(false);
        add(moveHistoryToolBar, BorderLayout.SOUTH);


    }

    public void addMoveToHistory(JComponent component) {
        moveHistoryList.add(component);
        revalidate();
        repaint();
    }
    public void removeMoveFromHistory() {
        if (moveHistoryList.getComponents().length == 0) return;
        moveHistoryList.remove(moveHistoryList.getComponents()[moveHistoryList.getComponents().length - 1]);
        revalidate();
        repaint();
    }
}
