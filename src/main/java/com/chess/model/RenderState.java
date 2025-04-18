package com.chess.model;

import java.util.List;
import java.util.Optional;

import com.chess.model.moves.Move;

public class RenderState {
    private final boolean[][] squaresActive;
    private final String[][] board;
    private final int[][] lastMove;

    public RenderState(List<Move> legalMoves, Board board, Move lastMove, Optional<Square> selectedSquare,
            boolean allowClicks) {

        String[][] tempBoard = new String[8][8];
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard.length; j++) {
                if (!board.isEmptyAt(i, j))
                    tempBoard[i][j] = board.getPieceAt(i, j).getUnicodeSymbol();
            }
        }
        this.lastMove = lastMove == null ? null
                : new int[][] {
                        { lastMove.getFrom().getRow(), lastMove.getFrom().getCol() },
                        { lastMove.getTo().getRow(), lastMove.getTo().getCol() },
                };
        boolean[][] tempSquaresActive = new boolean[8][8];
        this.board = tempBoard;
        if (!allowClicks) {
            this.squaresActive = tempSquaresActive;
            return;
        }
        if (!selectedSquare.isPresent()) {
            for (Move move : legalMoves) {
                tempSquaresActive[move.getFrom().getRow()][move.getFrom().getCol()] = true;
            }
        } else {
            for (Move move : legalMoves) {
                if (move.getFrom().equals(selectedSquare.get())) {
                    tempSquaresActive[move.getTo().getRow()][move.getTo().getCol()] = true;
                }
            }
            tempSquaresActive[selectedSquare.get().getRow()][selectedSquare.get().getCol()] = true;
        }

        this.squaresActive = tempSquaresActive;
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