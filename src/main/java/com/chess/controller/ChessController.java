package com.chess.controller;

import javax.swing.JProgressBar;

import com.chess.model.ChessModel;
import com.chess.model.PieceColor;
import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;
import com.chess.model.moves.PromotionMove;
import com.chess.model.pieces.PieceType;
import com.chess.player.BotPlayerMinimax;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.GamePanel;
import com.chess.view.ScreenManager;
import com.chess.view.Screens;

public class ChessController {
    private final GamePanel view;
    private final ChessModel model;
    private final Player whitePlayer;
    private final Player blackPlayer;
    // private final GameService gameService;

    public ChessController(Player whitePlayer, Player blackPlayer, GamePanel view) {
        this.model = new ChessModel();
        this.view = view;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        

        connectBotProgressBar();
        view.setControlListener(this::onControlClicked);
        view.refresh(model.getBoardSnapshot());
        nextTurn();
    }

    private void nextTurn() {
        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        if (current instanceof HumanPlayer)view.setSquareListener(this::onSquareClicked); 
        else view.setSquareListener((row, col) -> {System.out.println("disabled");});
        if (current != null) current.requestMove(model, (move) -> {
            model.executeMove(move);
            view.refresh(model.getBoardSnapshot());
            if (MoveValidator.getLegalMovesForColor(model.getGameState()).isEmpty()) {
                view.mate(model.getTurnColor().toString());
                return;
            }
            nextTurn();
        });
    }

    public void humanPlayerApplyMove(Move move) {
        Player current = model.getTurnColor() == PieceColor.BLACK ? blackPlayer : whitePlayer;
        current.onUserMove(move);
    }

    private void onSquareClicked(int row, int col) {
        boolean[][] isActiveSquares = model.getBoardSnapshot().getSquaresActive();
        if (!isActiveSquares[row][col])
            return;
        
        Move move = model.onSquareClicked(row, col);


        if (move != null && move instanceof PromotionMove) {
            view.showPromotionDialog(selectedType -> {
                PieceType newType = switch (selectedType) {
                    case "Queen" -> PieceType.QUEEN;
                    case "Rook" -> PieceType.ROOK;
                    case "Bishop" -> PieceType.BISHOP;
                    case "Knight" -> PieceType.KNIGHT;
                    default -> PieceType.QUEEN; // fallbac k
                };
                Move promotedMove = new PromotionMove(move.getFrom(), move.getTo(), newType);
                humanPlayerApplyMove(promotedMove);
                view.refresh(model.getBoardSnapshot());
            });
        }
        else if (move != null)
            humanPlayerApplyMove(move);
        view.refresh(model.getBoardSnapshot());

    }

    private void onControlClicked(String type) {
        switch (type) {
            case "Undo" -> {
                model.undoMove();
                model.undoMove();
            }
            case "Menu" -> {
                ScreenManager.showScreen(Screens.MENU);
            }

            default -> throw new IllegalArgumentException("Unknown Control button type: " + type);
        }
        view.refresh(model.getBoardSnapshot());

    }

    private void connectBotProgressBar() {
        BotPlayerMinimax botPlayer = null;
        if (whitePlayer instanceof BotPlayerMinimax botPlayer1) {
            botPlayer = botPlayer1;
        }
        else if (blackPlayer instanceof BotPlayerMinimax botPlayer1) {
            botPlayer = botPlayer1;
        }

        if (botPlayer != null) {
            JProgressBar bar = view.initBotProgress();
    
            botPlayer.connectProgressBar((Integer percentage) -> {
                System.out.println(percentage);
                bar.setValue(percentage);
            });
        }
        else {
            view.removeProgress();
        }
    }

}
