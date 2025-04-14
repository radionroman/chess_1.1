package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.PieceColor;
import com.chess.player.BotPlayer;
import com.chess.player.BotPlayerMinimax;
import com.chess.player.BotPlayerRandom;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.ChessView;

public class ChessController {
    private final ChessView view;
    private final ChessModel model;
    private HumanPlayer whitePlayer = new HumanPlayer();
    private Player blackPlayer = new BotPlayerMinimax();

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row, col) -> {
            handleClickBoard(row, col);
        });
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getBoardState(true));
        nextTurn();
    }

    private void nextTurn() {

        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        current.requestMove(model, (move) -> {
            System.out.println("move was made");
            model.applyMove(move);
            System.out.println("move was applied");
            if (current instanceof BotPlayer)
                view.refresh(model.getBoardState(false));
            else
                view.refresh(model.getBoardState(true));
            System.out.println("view was refreshed");
            if (model.getGameState().getLegalMovesForColor(model.getTurnColor()).isEmpty()) {
                System.out.println("MATE");
                view.mate(model.getTurnColor().toString());
                return;
            }
            // Force repaint before the next logic runs
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (current instanceof BotPlayer) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(200); // allow UI to update
                        } catch (InterruptedException ex) {
                        }
                        javax.swing.SwingUtilities.invokeLater(this::nextTurn);
                    }).start();
                } else {
                    nextTurn(); // Immediate for human
                }
            });
            // Immediately continue if it's human's turn
        });
    }

    public void userMoved(Move move) {
        whitePlayer.onUserMove(move);
    }

    private void handleClickBoard(int row, int col) {
        Move move = model.processBoardClicked(row, col);
        if (move != null)
            userMoved(move);
        view.refresh(model.getBoardState(true));
    }

    private void handleClickControl(String type) {
        switch (type) {
            case "Undo" -> {
                model.undo();
                model.undo();
                if (model.getTurnColor() == PieceColor.BLACK)
                    nextTurn();
            }

            case "PVP" -> {
                whitePlayer = new HumanPlayer();
                blackPlayer = new HumanPlayer();

            }
            case "PVE_RANDOM_WHITE" -> {

                whitePlayer = new HumanPlayer();
                blackPlayer = new BotPlayerRandom();

            }
            case "PVE_RANDOM_BLACK" -> {

                whitePlayer = new HumanPlayer();
                blackPlayer = new BotPlayerRandom();

            }

            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getBoardState(true));

    }

}
