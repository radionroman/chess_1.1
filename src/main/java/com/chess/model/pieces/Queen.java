package com.chess.model.pieces;

import com.chess.model.PieceColor;

public class Queen extends LongRangePiece{
    public Queen(PieceColor color){
        super(color, PieceType.QUEEN);
    }

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] {
            {1,1},
            {1,0},
            {0,1},
            {1,-1},
            {-1,-1},
            {-1,0},
            {0,-1},
            {-1,1}
        };
    }
    

}
