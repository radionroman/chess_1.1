package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.Square;

public abstract class LongRangePiece extends Piece {
    public LongRangePiece(PieceColor color, PieceType type, int rank) {
        super(color, type, rank);

    }

    protected abstract int[][] getMoveDirections();

    @Override
    public List<Square> getLegalMoves(Board board, int row, int col) {
        int[][] moveDirection = getMoveDirections();
        List<Square> moves = new ArrayList<>();
        for (int[] dir : moveDirection) {
            int r = row + dir[0];
            int c = col + dir[1];

            while (isInsideBoard(r, c)) {
                if (board.isEmptyAt(r,c)) {
                    moves.add(new Square(r, c));
                } else {
                    if (board.getPieceAt(r, c).getPieceColor() != this.getPieceColor()) {
                        moves.add(new Square(r, c));
                    }
                    break;
                }

                r += dir[0];
                c += dir[1];
            }

        }
        return moves;
    }

}
