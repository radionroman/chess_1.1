package com.chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.PieceColor;
import com.chess.model.Square;


public class King extends Piece{
    private int[][] moveDirections = new int[][]{
        {1,1},
        {1,0},
        {0,1},
        {1,-1},
        {-1,-1},
        {-1,0},
        {0,-1},
        {-1,1}
    };
    public King(PieceColor color){
        super(color, PieceType.KING);
    }

    @Override
    public List<Square> getLegalMoves(Piece[][] board, int row, int col) {
        List<Square> moves = new ArrayList<>();
        for (int[] dir : moveDirections) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (!isInsideBoard(r, c)) continue;
            if (board[r][c] != null && board[r][c].getPieceColor() == this.getPieceColor()) continue;
            moves.add(new Square(r,c));
            
        }
        return moves;
    }
}
