package com.chess.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


// TODO: Better UI architecture
public class MainView extends JFrame{

    private final JPanel menuPanel, mainPanel;
    private final JButton PVPButton, PVEWhiteButton, PVEBlackButton;
    private final CardLayout cardLayout= new CardLayout();
    private final ChessView chessView;
     
    public MainView(){
        menuPanel = new JPanel();
        PVPButton = new JButton("PVP");
        PVEWhiteButton = new JButton("PVE White");
        PVEBlackButton = new JButton("PVE Black");
        
        chessView = new ChessView();
        mainPanel = new JPanel(cardLayout);
        menuPanel.setBackground(new Color(150,75,0));
        chessView.setBackground(new Color(150,75,0));
        menuPanel.setOpaque(true);
        mainPanel.add(menuPanel, "menuPanel");
        mainPanel.add(chessView, "chessPanel");
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Clear panel first (optional)
menuPanel.removeAll();

// Add vertical glue to center buttons vertically
menuPanel.add(Box.createVerticalGlue());

JButton[] buttons = { PVPButton, PVEWhiteButton, PVEBlackButton };
for (JButton button : buttons) {
    button.setPreferredSize(new Dimension(300, 150));
    button.setMaximumSize(new Dimension(300, 150));
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setFont(new Font("SansSerif", Font.BOLD, 32));
    menuPanel.add(button);
    menuPanel.add(Box.createVerticalStrut(20)); // spacing between buttons
}

// Add vertical glue again to push content to center
menuPanel.add(Box.createVerticalGlue());

menuPanel.revalidate();
menuPanel.repaint();
        add(mainPanel);
        pack();
        setVisible(true);
    }

    public void setUpListeners(Consumer<String> callback) {
        PVPButton.addActionListener(e -> {
                callback.accept("PVP");
                cardLayout.show(mainPanel, "chessPanel");
        });
        PVEWhiteButton.addActionListener(e -> {
            callback.accept("PVEWhite");
            cardLayout.show(mainPanel, "chessPanel");
        });
        PVEBlackButton.addActionListener(e -> {
            callback.accept("PVEBlack");
            cardLayout.show(mainPanel, "chessPanel");
        });
    }

    

    public ChessView getChessView() {
        return chessView;
    }


}
