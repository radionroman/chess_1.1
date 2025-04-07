package com.chess.model.pieces;

import com.chess.model.PieceColor;

public abstract class Piece {
    private final PieceColor color;
    private String symbol;
    public Piece(PieceColor color){
        this.color = color;
    }

    public void setChar(String c){
        symbol = c;
    }

    public String getChar(){
        return symbol;
    }

    public PieceColor getPieceColor(){
        return color;
    }
}
