package com.chess.model.pieces;

import static com.chess.utils.Constants.BOARD_COLS;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.Square;
import com.chess.model.moves.CastlingMove;
import com.chess.model.moves.DefaultMove;
import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;

public class King extends Piece {
    private final int[][] moveDirections = new int[][] {
            { 1, 1 },
            { 1, 0 },
            { 0, 1 },
            { 1, -1 },
            { -1, -1 },
            { -1, 0 },
            { 0, -1 },
            { -1, 1 }
    };

    public King(PieceColor color) {
        super(color, PieceType.KING, 1000000);
    }

    @Override
    public List<Move> getPseudoLegalMoves(GameState state, int row, int col) {
        Board board = state.getBoard();

        List<Move> moves = new ArrayList<>();
        Square from = new Square(row, col);

        for (int[] dir : moveDirections) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (!isInsideBoard(r, c))
                continue;
            if (!board.isEmptyAt(r, c) && board.getPieceAt(r, c).getPieceColor() == this.getPieceColor())
                continue;
            if (adjacentToOtherKing(board, r, c))
                continue;
            moves.add(new DefaultMove(from, new Square(r, c)));

        }
        moves.addAll(getCastlingMoves(0, getPieceColor(), state, row, col));
        moves.addAll(getCastlingMoves(BOARD_COLS - 1, getPieceColor(), state, row, col));

        return moves;
    }

    private List<Move> getCastlingMoves(int rookCol, PieceColor color, GameState state, int row, int col) {
        Board board = state.getBoard();

        ArrayList<Move> moves = new ArrayList<>();
        if (hasMoved())
            return moves;
        Square kingSquare = new Square(row, col);
        int kingCol = kingSquare.getCol();
        Square rookSquare = new Square(kingSquare.getRow(), rookCol);
        Piece rook = board.getPieceAt(rookSquare);
        if (rook == null || rook.hasMoved())
            return moves;
        int start, end;

        if (kingCol > rookCol) {
            start = rookCol + 1;
            end = kingCol;
        } else {
            start = kingCol;
            end = rookCol - 1;
        }
        for (int i = start; i <= end; i++) {
            if (MoveValidator.isSquareAttacked(color, state, new Square(kingSquare.getRow(), i))
                    || (i != kingCol && !board.isEmptyAt(kingSquare.getRow(), i))) {
                return moves;
            }
        }
        if (kingCol < rookCol)
            moves.add(new CastlingMove(kingSquare, new Square(kingSquare.getRow(), end),
                    false));
        else
            moves.add(new CastlingMove(kingSquare, new Square(kingSquare.getRow(),
                    start), true));
        return moves;

    }

    private boolean adjacentToOtherKing(Board board, int row, int col) {
        for (int[] dir : moveDirections) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (isInsideBoard(r, c)) {
                Piece piece = board.getPieceAt(r, c);
                if (piece != null && piece.getPieceType() == PieceType.KING
                        && piece.getPieceColor() != this.getPieceColor()) {
                    return true;
                }
            }
        }
        return false;
    }
}
