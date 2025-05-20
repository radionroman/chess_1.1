package com.chess.controller;

import com.chess.model.PieceColor;
import com.chess.player.BotPlayerMinimax;
import com.chess.player.HumanPlayer;
import com.chess.view.GamePanel;
import com.chess.view.MainView;

public class MainController {
    private ChessController chessController;
    private GamePanel gamePanel;
    private MainView mainView;

    public MainController() {
        this.mainView = new MainView();
        mainView.getMenuPanel().setMenuListener((type) -> {
            switch (type) {
                case "PVP" -> chessController = new ChessController(new HumanPlayer(), new HumanPlayer(),
                        mainView.getGamePanel());
                case "PVEWhite" -> {
                    chessController = new ChessController(new HumanPlayer(), new BotPlayerMinimax(2, PieceColor.BLACK),
                        mainView.getGamePanel());
                        }

                case "PVEBlack" -> {chessController = new ChessController(new BotPlayerMinimax(3,PieceColor.WHITE), new BotPlayerMinimax(2, PieceColor.BLACK),
                        mainView.getGamePanel());
                    
                    }
                default -> throw new AssertionError();
            }
        });

    }

}
