package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.PieceColor;
import com.chess.model.Square;


public class Pawn extends Piece{
    public Pawn(PieceColor color){
        super(color);
        super.setChar("Pawn");
    }

    @Override
    public void updateLegalMoves(Piece[][] board, int row, int col) {
        List<Square> moves = new ArrayList<>();

        // Basic forward movement for white pawn
        if (getPieceColor() == PieceColor.WHITE) {
            if (row > 0 && board[row - 1][col] == null) {
                moves.add(new Square(row - 1, col));
            }

            // Optional: allow 2-square move from starting row
            if (row == 6 && board[row - 1][col] == null && board[row - 2][col] == null) {
                moves.add(new Square(row - 2, col));
            }
        }
        super.setLegalMoves(moves);

    }
}
