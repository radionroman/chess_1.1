package com.chess.model;

public class GameState {
    private final Board board;
    private PieceColor turnColor;
    private Move lastMove = null;

    public GameState() {
        board = new Board();
        board.init();
        turnColor = PieceColor.WHITE;
    }

    public GameState(Board board, PieceColor turnColor, Move lastMove) {
        this.board = board.copy();
        this.turnColor = turnColor;
        this.lastMove = lastMove;
    }

    public GameState copy() {
        GameState gameStateCopy = new GameState(board, turnColor, lastMove);
        return gameStateCopy;
    }



    public void movePiece(Move move) {
        lastMove = move;
        board.movePiece(move, turnColor);
        turnColor = turnColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

    }

    public Move getLastMove(){
        return lastMove;
    }


    // Basic Getters
    public Board getBoard() {
        return board;
    }

    public PieceColor getTurnColor() {
        return turnColor;
    }

    // Advanced getters






}
