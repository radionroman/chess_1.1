package com.chess.model;

import com.chess.model.moves.Move;
import static com.chess.utils.Constants.DEFAULT_BOARD;

public class GameState {
    private final Board board;
    private PieceColor turnColor;
    private Move previousMove = null;

    public GameState() {
        board = new Board();
        board.setup(DEFAULT_BOARD);
        turnColor = PieceColor.WHITE;
    }

    public GameState(Board board, PieceColor turnColor, Move lastMove){
        this.board = board;
        this.turnColor = turnColor;
        this.previousMove = lastMove;
    }

    public void switchTurnColor() {
        turnColor = turnColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }

    public GameState copy() {
        Board b2 = board.copy();              // you’ll need to implement Board.copy()
        GameState c = new GameState(b2, turnColor, previousMove);
        return c;
    }

    public  void executeMove(Move move) {
        if (board.getPieceAt(move.getFrom()) == null) {
            throw new IllegalStateException(
                "Cannot move from empty square " + move.getFrom());
        }

        move.setPreviousMove(previousMove);
        move.execute(board);

        previousMove = move;
        switchTurnColor();
    }

    public void undoMove() {
        if (board.getPieceAt(previousMove.getTo()) == null) {
            throw new IllegalStateException(
                "Cannot undo the move to the square: " + previousMove.getTo());
        }
        
        previousMove.undo(board);
        previousMove = previousMove.getPreviousMove();

        switchTurnColor();
    }

    public Move getLastMove() {
        return previousMove;
    }

    public Board getBoard() {
        return board;
    }

    public PieceColor getTurnColor() {
        return turnColor;
    }

}
