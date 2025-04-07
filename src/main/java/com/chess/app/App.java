package com.chess.app;

import com.chess.controller.ChessController;
import com.chess.model.ChessModel;
import com.chess.view.ChessView;

/**
 * Hello world!
 *`
 */
public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ChessModel model = new ChessModel();
            ChessView view = new ChessView();
            new ChessController(model, view);
        });

    }
}
