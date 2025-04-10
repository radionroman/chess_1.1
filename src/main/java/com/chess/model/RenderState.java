package com.chess.model;

import java.util.List;

import com.chess.model.pieces.Piece;

public class RenderState {
    private final boolean[][] squaresActive;
    private final String[][] board;
    private final int[][] lastMove;

    public RenderState(List<Move> legalMoves, Piece[][] board, Move lastMove) {
        
        String[][] tempBoard = new String[8][8];
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard.length; j++) {
                if (board[i][j] != null) tempBoard[i][j] = board[i][j].getUnicodeSymbol();
            }
        }
        this.lastMove = lastMove == null ? null : new int[][]{
            {lastMove.getFrom().getRow(),lastMove.getFrom().getCol() },
            {lastMove.getTo().getRow(),lastMove.getTo().getCol() },
        };
        this.board = tempBoard;
    }

    public boolean[][] getSquaresActive() {
        return squaresActive;
    }

    public String[][] getBoard() {
        return board;
    }

    public int[][] getLastMove() {
        return lastMove;
    }

    
    
}