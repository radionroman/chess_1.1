package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public abstract class Move {
    private final Square from;
    private final Square to;
    protected Piece captured = null;
    private Move previousMove = null;

    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }

    public void setPreviousMove(Move previousMove) {
        this.previousMove = previousMove;
    }

    public Move getPreviousMove() {
        return previousMove;
    }

    // TODO: make the method atomic
    public abstract void makeMove(Board board);

    public abstract void unMakeMove(Board board);

}
