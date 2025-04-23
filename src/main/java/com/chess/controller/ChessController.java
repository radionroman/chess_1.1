package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.PieceColor;
import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;
import com.chess.model.moves.PromotionMove;
import com.chess.model.pieces.PieceType;
import com.chess.player.BotPlayer;
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

    public ChessController(Player whitePlayer, Player blackPlayer, GamePanel view) {
        this.model = new ChessModel();
        this.view = view;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        view.setBoardClickedListeners((row, col) -> {
            handleClickBoard(row, col);
        });
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getRenderState(isClickable()));
        nextTurn();
    }

    private boolean isClickable() {
        PieceColor turnColor = model.getTurnColor();
        Player current;
        if (turnColor == PieceColor.WHITE)
            current = whitePlayer;
        else
            current = blackPlayer;
        return current instanceof HumanPlayer;
    }

    private void nextTurn() {

        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        current.requestMove(model, (move) -> {
            model.applyMove(move);
            if (current instanceof BotPlayer)
                view.refresh(model.getRenderState(isClickable()));
            else
                view.refresh(model.getRenderState(isClickable()));
            if (MoveValidator.getLegalMovesForColor(model.getGameState()).isEmpty()) {
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
        Player current = model.getTurnColor() == PieceColor.BLACK ? blackPlayer : whitePlayer;
        current.onUserMove(move);
    }

    private void handleClickBoard(int row, int col) {

        boolean[][] isActiveSquares = model.getRenderState(true).getSquaresActive();
        if (!isActiveSquares[row][col])
            return;
        Move move = model.processBoardClicked(row, col);

        if (move != null && move instanceof PromotionMove) {
            view.showPromotionDialog(selectedType -> {
                PieceType newType = switch (selectedType) {
                    case "Queen" -> PieceType.QUEEN;
                    case "Rook" -> PieceType.ROOK;
                    case "Bishop" -> PieceType.BISHOP;
                    case "Knight" -> PieceType.KNIGHT;
                    default -> PieceType.QUEEN; // fallback
                };
                Move promotedMove = new PromotionMove(move.getFrom(), move.getTo(), newType);
                userMoved(promotedMove);
                view.refresh(model.getRenderState(isClickable()));
            });
        }

        else if (move != null)
            userMoved(move);
        view.refresh(model.getRenderState(isClickable()));

    }

    private void handleClickControl(String type) {

        switch (type) {
            case "Undo" -> {
                model.undo();
                model.undo();
            }
            case "Menu" -> {
                ScreenManager.showScreen(Screens.MENU);
            }

            default -> throw new IllegalArgumentException("Unknown Control button type: " + type);
        }
        view.refresh(model.getRenderState(isClickable()));

    }

}
