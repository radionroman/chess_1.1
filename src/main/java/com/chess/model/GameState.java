package com.chess.model;

import com.chess.model.moves.Move;
import static com.chess.utils.Constants.*;

public class GameState {
    private final Board board;
    private PieceColor turnColor;
    private Move lastMove = null;

    public GameState() {
        board = new Board();
        board.setup(DEFAULT_BOARD);
        turnColor = PieceColor.WHITE;
    }

    public GameState(Board board, PieceColor turnColor, Move lastMove) {
        this.board = board.copy();
        this.turnColor = turnColor;
        this.lastMove = lastMove;
    }

    public GameState copy() {
        GameState gameStateCopy = new GameState(board, turnColor, lastMove);
        return gameStateCopy;
    }

    public void switchTurnColor() {
        turnColor = turnColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

    }

    public void movePiece(Move move) {
        move.setPreviousMove(lastMove);
        lastMove = move;
        move.makeMove(board);
        switchTurnColor();
    }

    public void undoMove() {
        lastMove.unMakeMove(board);
        lastMove = lastMove.getPreviousMove();
        switchTurnColor();
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
