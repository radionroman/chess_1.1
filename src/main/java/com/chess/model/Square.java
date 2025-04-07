package com.chess.model;

public class Square {
    private int row, col;
    public Square(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
    public boolean isEqual(Square s2){
        return this.col == s2.getCol() && this.row == s2.getRow();
    }

}
