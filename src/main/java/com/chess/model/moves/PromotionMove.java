package com.chess.model.moves;

import com.chess.model.Board;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;

public class PromotionMove extends Move{
    private final PieceType promoteTo;
    private Piece pawnToRevert;

    public PromotionMove(Square from, Square to, PieceType promoteTo) {
        super(from, to);
        this.promoteTo = promoteTo;
    }
    @Override
    public void execute(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        captured = board.getPieceAt(to);
        pawnToRevert = board.getPieceAt(from);
        board.setPieceAt(to, PieceFactory.createPiece(promoteTo, board.getPieceAt(from).getColor()));
        board.setPieceAt(from, null);
        board.getPieceAt(to).setHasMoved();
            
    }
    @Override
    public void undo(Board board) {
        Square from = this.getFrom();
        Square to = this.getTo();
        board.setPieceAt(to, captured);
        board.setPieceAt(from, pawnToRevert);
    }
    

}
