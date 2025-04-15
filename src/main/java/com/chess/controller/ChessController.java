package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.MoveGenerator;
import com.chess.model.MoveType;
import com.chess.model.PieceColor;
import com.chess.player.BotPlayer;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.ChessView;

public class ChessController {
    private final ChessView view;
    private final ChessModel model;
    private HumanPlayer whitePlayer = new HumanPlayer();
    private HumanPlayer blackPlayer = new HumanPlayer();
    // private Player blackPlayer = new BotPlayerMinimax();

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row, col) -> {
            handleClickBoard(row, col);
        });
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getRenderState(true));
        nextTurn();
    }

    private void nextTurn() {

        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        current.requestMove(model, (move) -> {
            System.out.println("move was made");
            model.applyMove(move);
            System.out.println("move was applied");
            if (current instanceof BotPlayer)
                view.refresh(model.getRenderState(false));
            else
                view.refresh(model.getRenderState(true));
            System.out.println("view was refreshed");
            if (MoveGenerator.getLegalMovesForColor(model.getGameState()).isEmpty()) {
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
                view.refresh(model.getRenderState(true));
                return;
            });
        }
        if (move != null)
            userMoved(move);
        view.refresh(model.getRenderState(true));

    }

    private void handleClickControl(String type) {
        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        switch (type) {
            case "Undo" -> {
                model.undo();
                model.undo();
            }


            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getRenderState(true));

    }

}
