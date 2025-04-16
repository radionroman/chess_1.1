package com.chess.controller;

import com.chess.player.BotPlayerMinimax;
import com.chess.player.HumanPlayer;
import com.chess.view.ChessView;
import com.chess.view.MainView;

public class MainController {
    private ChessController chessController;
    private ChessView chessView;
    private MainView mainView;

    public MainController() {
        this.mainView = new MainView();
        mainView.setUpListeners((type) -> {
            switch (type) {
                case "PVP" -> chessController = new ChessController(new HumanPlayer(), new HumanPlayer(), mainView.getChessView());
                case "PVEWhite" -> chessController = new ChessController(new HumanPlayer(), new BotPlayerMinimax(), mainView.getChessView());
                case "PVEBlack" -> chessController = new ChessController(new BotPlayerMinimax(), new HumanPlayer(), mainView.getChessView());
                default -> throw new AssertionError();
            }
        });
    }

    
    

}
