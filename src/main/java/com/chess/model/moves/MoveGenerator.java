package com.chess.model.moves;

import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.PieceOnSquare;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceType;

public class MoveGenerator {
    public MoveGenerator() {

    }

    static long counter = 0;

    public static List<Move> getLegalMovesForColor(GameState state) {
        // System.out.println(counter++);
        PieceColor color = state.getTurnColor();
        // long start = System.nanoTime();
        List<Move> pseudoLegalMoves = getPseudoLegalMovesForColor(state);
        // long end = System.nanoTime();
        // System.out.println(end - start);
        ArrayList<Move> legalMoves = new ArrayList<>();
        GameState testState = state.copy();
        for (Move move : pseudoLegalMoves) {
            testState.movePiece(move);
            if (!testState.getBoard().isCheckPresent(color))
                legalMoves.add(move);
            testState.undoMove();
        }
        return legalMoves;
    }

    private static List<Move> getPseudoLegalMovesForColor(GameState state) {
        Board board = state.getBoard();
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> pieceMoves;
        List<PieceOnSquare> pieces = getPiecesOnSquare(state);
        for (PieceOnSquare pieceOnSquare : pieces) {

            if (pieceOnSquare.getPiece().getPieceType() == PieceType.PAWN) { // promotion and en passant checks

                if (!getPromotionMoves(pieceOnSquare, state).isEmpty()) {
                    moves.addAll(getPromotionMoves(pieceOnSquare, state));
                    continue;
                }
                moves.addAll(getEnPassantMoves(pieceOnSquare, state));

            }
            pieceMoves = pieceOnSquare.getLegalMoves(board, state);

            moves.addAll(pieceMoves);
            if (pieceOnSquare.getPiece().getPieceType() == PieceType.KING) { // castling checks

                moves.addAll(getCastlingMoves(pieceOnSquare, 0, state));
                moves.addAll(getCastlingMoves(pieceOnSquare, 7, state));
            }

        }

        return moves;
    }

    private static List<Move> getEnPassantMoves(PieceOnSquare pieceOnSquare, GameState state) {
        ArrayList<Move> moves = new ArrayList<>();
        Square from = pieceOnSquare.getSquare();
        Board board = state.getBoard();
        Move lastMove = state.getLastMove();

        if (from.getRow() != (pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? 3 : 4))
            return moves;

        int direction = pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? -1 : 1;
        Square leftCaptured = new Square(from.getRow(), from.getCol() - 1);
        Square rightCaptured = new Square(from.getRow(), from.getCol() + 1);
        Square captured;
        // System.out.println(lastMove);
        if (lastMove.getTo().equals(leftCaptured))
            captured = leftCaptured;
        else if (lastMove.getTo().equals(rightCaptured))
            captured = rightCaptured;
        else
            return moves;
        // System.out.println("ENPAssANT CHECL");
        if (!lastMove.getFrom().equals(new Square(captured.getRow() + direction * 2, captured.getCol())))
            return moves;
        if (board.getPieceAt(lastMove.getTo()).getPieceType() != PieceType.PAWN)
            return moves;
        moves.add(new EnPassantMove(from, new Square(captured.getRow() + direction, captured.getCol())));
        return moves;
    }

    private static List<Move> getCastlingMoves(PieceOnSquare pieceOnSquare, int rookCol, GameState state) {
        // System.out.println("Castling entered!");
        ArrayList<Move> moves = new ArrayList<>();
        PieceColor color = state.getTurnColor();
        Board board = state.getBoard();
        Piece king = pieceOnSquare.getPiece();
        if (king.hasMoved())
            return moves;
        // System.out.println("King hasnt moved!");
        Square kingSquare = pieceOnSquare.getSquare();
        int kingCol = kingSquare.getCol();
        Square rookSquare = new Square(kingSquare.getRow(), rookCol);
        Piece rook = board.getPieceAt(rookSquare);
        if (rook == null || rook.hasMoved())
            return moves;
        // System.out.println("Rook is present and hasnt moved!");
        int start, end;

        if (kingCol > rookCol) {
            start = rookCol + 1;
            end = kingCol;
        } else {
            start = kingCol;
            end = rookCol - 1;
        }
        // System.out.println("Checking for castling: " + start + " " + end);
        for (int i = start; i <= end; i++) {
            if (board.isCheckPresent(color, new Square(kingSquare.getRow(), i))
                    || (i != kingCol && !board.isEmptyAt(kingSquare.getRow(), i))) {
                // System.out.println("Checking for castling failed.");
                return moves;
            }
        }
        if (kingCol < rookCol)
            moves.add(new CastlingMove(kingSquare, new Square(kingSquare.getRow(), end), false));
        else
            moves.add(new CastlingMove(kingSquare, new Square(kingSquare.getRow(), start), true));
        // System.out.println("Castling is possible");
        return moves;

    }

    private static List<Move> getPromotionMoves(PieceOnSquare pieceOnSquare, GameState state) {
        ArrayList<Move> moves = new ArrayList<>();
        Square from = pieceOnSquare.getSquare();
        Piece pawn = pieceOnSquare.getPiece();
        if (from.getRow() != (pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? 1 : BOARD_ROWS - 2))
            return moves;
        List<Square> squaresTo = pieceOnSquare.getLegalMovesTo(state.getBoard());
        for (Square square : squaresTo) {
            moves.add(new PromotionMove(from, square, PieceType.QUEEN));
            moves.add(new PromotionMove(from, square, PieceType.ROOK));
            moves.add(new PromotionMove(from, square, PieceType.KNIGHT));
            moves.add(new PromotionMove(from, square, PieceType.BISHOP));
        }

        return moves;

    }

    private static List<PieceOnSquare> getPiecesOnSquare(GameState state) {
        PieceColor color = state.getTurnColor();
        Board board = state.getBoard();
        ArrayList<PieceOnSquare> pieces = new ArrayList<>();
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (board.isEmptyAt(i, j))
                    continue;
                if (board.getPieceAt(i, j).getPieceColor() != color)
                    continue;
                pieces.add(new PieceOnSquare(board.getPieceAt(i, j), new Square(i, j)));
            }
        }
        return pieces;
    }
}
