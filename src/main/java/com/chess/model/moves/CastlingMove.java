package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;

public class CastlingMove extends Move {
    private final boolean isLong;

    public CastlingMove(Square from, Square to, boolean isLong) {
        super(from, to);
        this.isLong = isLong;
    }

    @Override
    public void makeMove(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(from);
        board.setPieceAt(to, piece);
        board.setPieceAt(from, null);
        if (isLong) {
            board.setPieceAt(from.getRow(), 2, board.getPieceAt(from.getRow(), 0));
            board.setPieceAt(from.getRow(), 0, null);
            board.getPieceAt(from.getRow(), 2).setHasMoved();
        } else {
            board.setPieceAt(from.getRow(), 5, board.getPieceAt(from.getRow(), 7));
            board.setPieceAt(from.getRow(), 7, null);
            board.getPieceAt(from.getRow(), 5).setHasMoved();
        }

        board.getPieceAt(to).setHasMoved();
    }

    @Override
    public void unMakeMove(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        board.setPieceAt(from.getRow(), 4, board.getPieceAt(to));
        board.setPieceAt(to, null);
        if (isLong) {
            board.setPieceAt(from.getRow(), 0, board.getPieceAt(from.getRow(), 2));
            board.setPieceAt(from.getRow(), 2, null);
            board.getPieceAt(from.getRow(), 0).setHasNotMoved();

        } else {
            board.setPieceAt(from.getRow(), 7, board.getPieceAt(from.getRow(), 5));
            board.setPieceAt(from.getRow(), 5, null);
            board.getPieceAt(from.getRow(), 7).setHasNotMoved();
        }
        board.getPieceAt(from).setHasNotMoved();
    }

}
