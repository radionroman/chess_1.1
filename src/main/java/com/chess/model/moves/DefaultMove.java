package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public class DefaultMove extends Move {

    public DefaultMove(Square from, Square to) {
        super(from, to);
    }

   

    @Override
    public void execute(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(from);
        if (piece == null){ 
            System.out.println("Trying to move a piece that is null!" + this);
            return;
        }
        captured = board.getPieceAt(to);
        board.setPieceAt(to, piece);
        board.setPieceAt(from, null);
        setPreviousHasMoved(piece.hasMoved()); 
        board.getPieceAt(to).setHasMoved();
    }

// DefaultMove.java
    @Override
    public void undo(Board board) {
        Square from = getFrom();
        Square to   = getTo();

        Piece moved = board.getPieceAt(to);
        // if there's no piece here **and** nothing was captured, this move was never applied
        if (moved == null && captured == null) {
            return;
        }

        // restore whatever was on `to` (might be null)
        board.setPieceAt(to, captured);
        // put the moved piece back on `from`
        board.setPieceAt(from, moved);

        // if it hadn’t moved before, clear its moved-flag
        if (moved != null && !isPreviousHasMoved()) {
            moved.setHasNotMoved();
        }
    }

}
