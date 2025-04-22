package com.chess.model.pieces;

import static com.chess.utils.Constants.BOARD_ROWS;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.Square;
import com.chess.model.moves.DefaultMove;
import com.chess.model.moves.EnPassantMove;
import com.chess.model.moves.Move;
import com.chess.model.moves.PromotionMove;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN, 100);
    }

    @Override
    public List<Move> getPseudoLegalMoves(GameState state, int row, int col) {
        List<Square> squaresTo = new ArrayList<>();
        Board board = state.getBoard();
        int moveDirection = getPieceColor() == PieceColor.WHITE ? -1 : 1;
        int startingRow = (getPieceColor() == PieceColor.WHITE) ? 6 : 1;
        List<Move> moves = new ArrayList<>();

        if (isInsideBoard(row + moveDirection, col) && board.isEmptyAt(row + moveDirection, col)) {
            squaresTo.add(new Square(row + moveDirection, col));
        }

        if (row == startingRow && board.isEmptyAt(row + moveDirection, col)
                && board.isEmptyAt(row + moveDirection * 2, col)) {
            squaresTo.add(new Square(row + moveDirection * 2, col));
        }

        if (isInsideBoard(row + moveDirection, col + 1) && !board.isEmptyAt(row + moveDirection, col + 1)
                && board.getPieceAt(row + moveDirection, col + 1).getPieceColor() != this.getPieceColor()) {
            squaresTo.add(new Square(row + moveDirection, col + 1));
        }
        if (isInsideBoard(row + moveDirection, col - 1) && !board.isEmptyAt(row + moveDirection, col - 1)
                && board.getPieceAt(row + moveDirection, col - 1).getPieceColor() != this.getPieceColor()) {
            squaresTo.add(new Square(row + moveDirection, col - 1));
        }
        Square from = new Square(row, col);
        for (Square square : squaresTo) {
            if (from.getRow() == (getPieceColor() == PieceColor.WHITE ? 1 : BOARD_ROWS - 2)) {
                moves.add(new PromotionMove(from, square, PieceType.QUEEN));
                moves.add(new PromotionMove(from, square, PieceType.ROOK));
                moves.add(new PromotionMove(from, square, PieceType.KNIGHT));
                moves.add(new PromotionMove(from, square, PieceType.BISHOP));
            } else {
                moves.add(new DefaultMove(from, square));
            }
        }
        moves.addAll(getEnPassantMoves(state, row, col));

        return moves;
    }

    private List<Move> getEnPassantMoves(GameState state, int row, int col) {
        ArrayList<Move> moves = new ArrayList<>();
        Board board = state.getBoard();
        Square from = new Square(row, col);

        Move lastMove = state.getLastMove();

        if (from.getRow() != (getPieceColor() == PieceColor.WHITE ? 3 : 4))
            return moves;

        int direction = getPieceColor() == PieceColor.WHITE
                ? -1
                : 1;
        Square leftCaptured = new Square(from.getRow(), from.getCol() - 1);
        Square rightCaptured = new Square(from.getRow(), from.getCol() + 1);
        Square captured;
        if (lastMove.getTo().equals(leftCaptured))
            captured = leftCaptured;
        else if (lastMove.getTo().equals(rightCaptured))
            captured = rightCaptured;
        else
            return moves;
        if (!lastMove.getFrom().equals(new Square(captured.getRow() + direction * 2,
                captured.getCol())))
            return moves;
        if (board.getPieceAt(lastMove.getTo()).getPieceType() != PieceType.PAWN)
            return moves;
        moves.add(new EnPassantMove(from, new Square(captured.getRow() + direction,
                captured.getCol())));
        return moves;
    }

}
