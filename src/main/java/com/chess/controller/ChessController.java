package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.MoveGenerator;
import com.chess.model.MoveType;
import com.chess.model.PieceColor;
import com.chess.player.BotPlayer;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.ChessPanel;

public class ChessController {
    private final ChessPanel view;
    private final ChessModel model;
    private final Player whitePlayer;
    private final Player blackPlayer;

    public ChessController(Player whitePlayer, Player blackPlayer, ChessPanel view) {
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
        if (turnColor == PieceColor.WHITE) current = whitePlayer;
        else current = blackPlayer;
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
            if (MoveGenerator.getLegalMovesForColor(model.getGameState()).isEmpty()) {
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
        if (!isActiveSquares[row][col]) return;
        Move move = model.processBoardClicked(row, col);
        
        if (move != null && move.getType().toString().startsWith("PROMOTION")) {
            view.showPromotionDialog(selectedType -> {
                MoveType newType = switch (selectedType) {
                    case "Queen" -> MoveType.PROMOTION_QUEEN;
                    case "Rook" -> MoveType.PROMOTION_ROOK;
                    case "Bishop" -> MoveType.PROMOTION_BISHOP;
                    case "Knight" -> MoveType.PROMOTION_KNIGHT;
                    default -> MoveType.PROMOTION_QUEEN; // fallback
                };
                Move promotedMove = new Move(move.getFrom(), move.getTo(), move.getGameState(), newType);
                userMoved(promotedMove);
                view.refresh(model.getRenderState(isClickable()));
            });
        }
        
        else if (move != null)
            userMoved(move);
        view.refresh(model.getRenderState(isClickable()));

    }

    private void handleClickControl(String type) {
        // Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        switch (type) {
            case "Undo" -> {
                model.undo();
                model.undo();
            }


            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getRenderState(isClickable()));

    }

}
