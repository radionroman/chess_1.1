package com.chess.model.pieces;

import com.chess.model.PieceColor;

public class Bishop extends LongRangePiece{
    public Bishop(PieceColor color){
        super(color, PieceType.BISHOP);
    }

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] {
            {1,1},
            {1,-1},
            {-1,-1},
            {-1,1}
        };
    }
    

}
