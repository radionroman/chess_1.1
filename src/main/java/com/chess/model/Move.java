package com.chess.model;

public class Move {
    private final Square from;
    private final Square to;

    public Move(Square from, Square to){
        this.from = from;
        this.to = to;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    @Override
    public String toString() {
        return  from + " -> " + to;
    }
    

}
