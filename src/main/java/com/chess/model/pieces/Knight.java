package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.Square;

public class Knight extends Piece {
    private final int[][] moveDirections = new int[][] {
            { 2, 1 }, { 1, 2 }, { -2, 1 }, { -1, 2 }, { -2, -1 }, { -1, -2 }, { 2, -1 }, { 1, -2 },
    };

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT, 320);
    }

    @Override
    public List<Square> getLegalMoves(Board board, int row, int col) {
        List<Square> moves = new ArrayList<>();
        for (int[] dir : moveDirections) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (!isInsideBoard(r, c))
                continue;
            if (!board.isEmptyAt(r,c) && board.getPieceAt(r, c).getPieceColor() == this.getPieceColor())
                continue;
            moves.add(new Square(r, c));
        }
        return moves;
    }
}
