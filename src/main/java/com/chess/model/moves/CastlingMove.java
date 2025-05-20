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
    public void execute(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        Piece piece = board.getPieceAt(from);
        board.setPieceAt(to, piece);
        board.setPieceAt(from, null);
        System.out.println(from + " " + to);
        if (isLong) {
            board.setPieceAt(from.getRow(), from.getCol()-1, board.getPieceAt(from.getRow(), 0));
            board.setPieceAt(from.getRow(), 0, null);
            board.getPieceAt(from.getRow(), from.getCol()-1).setHasMoved();
        } else {
            board.setPieceAt(from.getRow(), from.getCol()+1, board.getPieceAt(from.getRow(), 7));
            board.setPieceAt(from.getRow(), 7, null);
            board.getPieceAt(from.getRow(), from.getCol()+1).setHasMoved();
        }

        board.getPieceAt(to).setHasMoved();
    }

    @Override
    public void undo(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        board.setPieceAt(from.getRow(), from.getCol(), board.getPieceAt(to));
        board.setPieceAt(to, null);
        if (isLong) {
            board.setPieceAt(from.getRow(), 0, board.getPieceAt(from.getRow(), from.getCol()-1));
            board.setPieceAt(from.getRow(), from.getCol()-1, null);
            board.getPieceAt(from.getRow(), 0).setHasNotMoved();

        } else {
            board.setPieceAt(from.getRow(), 7, board.getPieceAt(from.getRow(), from.getCol()+1));
            board.setPieceAt(from.getRow(), from.getCol()+1, null);
            board.getPieceAt(from.getRow(), 7).setHasNotMoved();
        }
        board.getPieceAt(from).setHasNotMoved();
    }

}
