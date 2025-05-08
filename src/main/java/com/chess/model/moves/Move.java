package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public abstract class Move {
    private final Square from;
    private final Square to;
    protected Piece captured = null;
    private Move previousMove = null;
    private boolean previousHasMoved;
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

    public boolean isPreviousHasMoved() {
        return previousHasMoved;
    }

    public void setPreviousHasMoved(boolean previousHasMoved) {
        this.previousHasMoved = previousHasMoved;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        result = prime * result + ((captured == null) ? 0 : captured.hashCode());
        result = prime * result + ((previousMove == null) ? 0 : previousMove.hashCode());
        result = prime * result + (previousHasMoved ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (to == null) {
            if (other.to != null)
                return false;
        } else if (!to.equals(other.to))
            return false;
        if (captured == null) {
            if (other.captured != null)
                return false;
        } else if (!captured.equals(other.captured))
            return false;
        if (previousMove == null) {
            if (other.previousMove != null)
                return false;
        } else if (!previousMove.equals(other.previousMove))
            return false;
        if (previousHasMoved != other.previousHasMoved)
            return false;
        return true;
    }

    

}
