package com.chess.controller;

import com.chess.player.BotPlayerMinimaxSwingWorker;
import com.chess.player.HumanPlayer;
import com.chess.view.GamePanel;
import com.chess.view.MainView;

public class MainController {
    private ChessController chessController;
    private GamePanel gamePanel;
    private MainView mainView;

    public MainController() {
        this.mainView = new MainView();
        mainView.getMenuPanel().setUpListeners((type) -> {
            switch (type) {
                case "PVP" -> chessController = new ChessController(new HumanPlayer(), new HumanPlayer(),
                        mainView.getGamePanel());
                case "PVEWhite" -> {
                    chessController = new ChessController(new HumanPlayer(), new BotPlayerMinimaxSwingWorker(2),
                        mainView.getGamePanel());
                        }

                case "PVEBlack" -> {chessController = new ChessController(new BotPlayerMinimaxSwingWorker(2), new HumanPlayer(),
                        mainView.getGamePanel());
                    
                    }
                default -> throw new AssertionError();
            }
        });

    }

}
