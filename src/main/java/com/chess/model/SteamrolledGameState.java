package com.chess.model;

import com.chess.model.pieces.Piece;

public class SteamrolledGameState {
    private final boolean[][] squaresActive;
    private final String[][] board;

    public SteamrolledGameState(boolean[][] squaresActive, Piece[][] board) {
        this.squaresActive = squaresActive;
        String[][] tempBoard = new String[8][8];
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard.length; j++) {
                if (board[i][j] != null) tempBoard[i][j] = board[i][j].getUnicodeSymbol();
            }
        }
        this.board = tempBoard;
    }

    public boolean[][] getSquaresActive() {
        return squaresActive;
    }

    public String[][] getBoard() {
        return board;
    }
    
}