package com.chess.controller;

import com.chess.player.BotPlayerMinimax;
import com.chess.player.HumanPlayer;
import com.chess.view.ChessPanel;
import com.chess.view.MainView;

public class MainController {
    private ChessController chessController;
    private ChessPanel chessView;
    private MainView mainView;

    public MainController() {
        this.mainView = new MainView();
        mainView.getMenuPanel().setUpListeners((type) -> {
            switch (type) {
                case "PVP" -> chessController = new ChessController(new HumanPlayer(), new HumanPlayer(),
                        mainView.getChessPanel());
                case "PVEWhite" -> chessController = new ChessController(new HumanPlayer(), new BotPlayerMinimax(3),
                        mainView.getChessPanel());
                case "PVEBlack" -> chessController = new ChessController(new BotPlayerMinimax(3), new HumanPlayer(),
                        mainView.getChessPanel());
                default -> throw new AssertionError();
            }
        });

    }

}
