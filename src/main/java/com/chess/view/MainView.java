package com.chess.view;

import javax.swing.JFrame;
import javax.swing.JPanel;


// TODO: Better UI architecture
public class MainView extends JFrame{

    private final JPanel mainPanel;
    private final MenuPanel menuPanel;

    private final GamePanel gamePanel;
     
    public MainView(){
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        mainPanel = new JPanel(ScreenManager.getLayout());
        ScreenManager.setPanel(mainPanel);
       
        mainPanel.add(menuPanel, Screens.MENU.toString());
        mainPanel.add(gamePanel, Screens.GAMEBOARD.toString());
        add(mainPanel);
        pack();
        setVisible(true);
    }
    

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

}
