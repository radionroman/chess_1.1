package com.chess.model.pieces;

import com.chess.model.PieceColor;

public class PieceFactory {

    public static Piece createPiece(PieceType type, PieceColor color) {
        return switch (type) {
            case PieceType.PAWN -> new Pawn(color);
            case PieceType.QUEEN -> new Queen(color);
            case PieceType.ROOK -> new Rook(color);
            case PieceType.BISHOP -> new Bishop(color);
            case PieceType.KNIGHT -> new Knight(color);
            case PieceType.KING -> new King(color);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }


}
