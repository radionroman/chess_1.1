package com.chess.model.pieces;

import com.chess.model.PieceColor;

public class Rook extends LongRangePiece {
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK, 500);
    }

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] {
                { 1, 0 },
                { 0, 1 },
                { -1, 0 },
                { 0, -1 },
        };
    }

}
