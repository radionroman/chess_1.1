package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.Square;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN, 100);
        super.setChar("Pawn");
    }

    @Override
    public List<Square> getLegalMoves(Board board, int row, int col) {
        List<Square> moves = new ArrayList<>();
        int moveDirection = getPieceColor() == PieceColor.WHITE ? -1 : 1;
        int startingRow = (getPieceColor() == PieceColor.WHITE) ? 6 : 1;
        // Basic forward movement for white pawn

        if (isInsideBoard(row + moveDirection, col) && board.isEmptyAt(row + moveDirection, col)) {
            moves.add(new Square(row + moveDirection, col));
        }

        // Optional: allow 2-square move from starting row
        if (row == startingRow && board.isEmptyAt(row + moveDirection, col)
                && board.isEmptyAt(row + moveDirection * 2, col)) {
            moves.add(new Square(row + moveDirection * 2, col));
        }

        if (isInsideBoard(row + moveDirection, col + 1) && !board.isEmptyAt(row + moveDirection, col+1)
                && board.getPieceAt(row + moveDirection, col+1).getPieceColor() != this.getPieceColor()) {
            moves.add(new Square(row + moveDirection, col + 1));
        }
        if (isInsideBoard(row + moveDirection, col - 1) && !board.isEmptyAt(row + moveDirection, col-1)
                && board.getPieceAt(row + moveDirection, col-1).getPieceColor() != this.getPieceColor()) {
            moves.add(new Square(row + moveDirection, col - 1));
        }
        return moves;
    }
}
