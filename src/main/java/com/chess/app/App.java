package com.chess.app;

import com.chess.controller.MainController;

// 
public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
        });

    }
}

