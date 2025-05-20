package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public class EnPassantMove extends Move{

    public EnPassantMove(Square from, Square to) {
        super(from, to);

    }

    @Override
    public void execute(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(from);
        captured = board.getPieceAt(from.getRow(), to.getCol());
        board.setPieceAt(to, piece);
        board.setPieceAt(from, null);
        board.setPieceAt(from.getRow(), to.getCol(), null);
        board.getPieceAt(to).setHasMoved();
    }
    @Override
    public void undo(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(to);
        board.setPieceAt(to, null);
        board.setPieceAt(from.getRow(), to.getCol(), captured);
        board.setPieceAt(from, piece);
    }
    


}
