package com.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.pieces.Piece;

public class PieceOnSquare {
    private final Piece piece;
    private final Square square;
    

    public PieceOnSquare(Piece piece, Square square) {
        this.piece = piece;
        this.square = square;
    }

    public List<Square> getLegalMovesTo(Board board){
        return piece.getLegalMoves(board, square.getRow(), square.getCol());
    }

    public List<Move> getLegalMoves(Board board, GameState gameState){
        List<Square> movesTo = piece.getLegalMoves(board, square.getRow(), square.getCol());
        ArrayList<Move> moves = new ArrayList<>();
        for (Square squareTo : movesTo) {
            moves.add(new Move(square, squareTo, gameState.copy(), MoveType.DEFAULT));
        }
        return moves;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getSquare() {
        return square;
    }
}
