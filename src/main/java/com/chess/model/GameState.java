package com.chess.model;

import com.chess.model.moves.Move;
import static com.chess.utils.Constants.DEFAULT_BOARD;

public class GameState {
    private final Board board;
    private PieceColor turnColor;
    private Move lastMove = null;

    public GameState() {
        board = new Board();
        board.setup(DEFAULT_BOARD);
        turnColor = PieceColor.WHITE;
    }

    public void switchTurnColor() {
        turnColor = turnColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

    }

    public void makeMove(Move move) {
        Move oldLast = lastMove;
        PieceColor oldColor = turnColor;
        try {
            move.setPreviousMove(lastMove);
            lastMove = move;
            move.makeMove(board);
            switchTurnColor();
        } catch (RuntimeException e) {
            move.unMakeMove(board);
            move.setPreviousMove(null);
            turnColor = oldColor;
            lastMove = oldLast;
            throw e;
        }
    }

    public void undoMove() {
        Move oldLast = lastMove;
        PieceColor oldColor = turnColor;
        try {
            lastMove.unMakeMove(board);
            lastMove = lastMove.getPreviousMove();
            switchTurnColor();
        } catch (RuntimeException e) {
            oldLast.makeMove(board);
            lastMove = oldLast;
            turnColor = oldColor;
            throw e;
        }
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Board getBoard() {
        return board;
    }

    public PieceColor getTurnColor() {
        return turnColor;
    }

}
