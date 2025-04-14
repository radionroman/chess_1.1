package com.chess.model;

public class Move {
    private final Square from;
    private final Square to;
    private final GameState gameState;
    private MoveType type = MoveType.DEFAULT;

    public Move(Square from, Square to, GameState gameState){
        this.from = from;
        this.to = to;
        this.gameState = gameState;
    }

    public Move(Square from, Square to, GameState gameState, MoveType type){
        this.from = from;
        this.to = to;
        this.gameState = gameState;
        this.type = type;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        return  from + " -> " + to;
    }

    public MoveType getType(){
        return type;
    }
    

}
