package com.chess.model.pieces;



import java.util.List;

import com.chess.model.*;


public abstract class Piece {
    private final PieceColor color;
    private String symbol;
    private List<Square> legalMoves;
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

    public List<Square> getLegalMoves(){
        return legalMoves;
    }

    public void setLegalMoves(List<Square> moves){
        legalMoves = moves;
    }

    public void updateLegalMoves(Piece[][] board, int row, int col) {

    }


}
