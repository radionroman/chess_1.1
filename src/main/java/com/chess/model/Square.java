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
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    

}
