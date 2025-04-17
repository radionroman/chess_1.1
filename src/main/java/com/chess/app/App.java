package com.chess.app;

import com.chess.controller.MainController;

// 
public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController MainController = new MainController();
            // ChessModel model = new ChessModel();
            // ChessView view = new ChessView();
            // new ChessController(model, view);
        });

    }
}
