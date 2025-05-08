package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public class DefaultMove extends Move {

    public DefaultMove(Square from, Square to) {
        super(from, to);
    }

   

    @Override
    public void makeMove(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(from);
        captured = board.getPieceAt(to);
        board.setPieceAt(to, piece);
        board.setPieceAt(from, null);
        setPreviousHasMoved(piece.hasMoved()); 
        board.getPieceAt(to).setHasMoved();
    }

    @Override
    public void unMakeMove(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(to);
        board.setPieceAt(to, captured);
        board.setPieceAt(from, piece);
        if (!isPreviousHasMoved())
            board.getPieceAt(from).setHasNotMoved();
    }

}
