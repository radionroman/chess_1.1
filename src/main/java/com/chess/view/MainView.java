package com.chess.view;

import javax.swing.JFrame;
import javax.swing.JPanel;


// TODO: Better UI architecture
public class MainView extends JFrame{

    private final JPanel mainPanel;
    private final MenuPanel menuPanel;

    private final ChessPanel chessPanel;
     
    public MainView(){
        menuPanel = new MenuPanel();
        chessPanel = new ChessPanel();
        mainPanel = new JPanel(ScreenManager.getLayout());
        ScreenManager.setPanel(mainPanel);
       
        
        mainPanel.add(menuPanel, Screens.MENU.toString());
        mainPanel.add(chessPanel, Screens.GAMEBOARD.toString());
        add(mainPanel);
        pack();
        setVisible(true);
    }
    

    public ChessPanel getChessPanel() {
        return chessPanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

}
