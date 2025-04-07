package com.chess.model.pieces;

import com.chess.model.PieceColor;

public class Pawn extends Piece{
    public Pawn(PieceColor color){
        super(color);
        super.setChar("Pawn");
    }
}
