package com.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class MoveHistoryEntryComponent extends JPanel{
    private final JLabel label;
    private Color fromPrevious, toPrevious;

    public MoveHistoryEntryComponent(JButton from, JButton to, String text) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel(text);
        add(new JSeparator());
        add(label);
        add(new JSeparator());
        setMaximumSize(new Dimension(300, 60));
        setMinimumSize(new Dimension(300, 60));  
        setPreferredSize(new Dimension(300, 60));      // ← ADD THIS:
                
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fromPrevious = from.getBackground();
                toPrevious = to.getBackground();
                from.setBackground(Color.RED);
                to.setBackground(Color.GREEN);
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                to.setBackground(toPrevious);
                from.setBackground(fromPrevious);
                repaint();

            }
        });
    }

}
