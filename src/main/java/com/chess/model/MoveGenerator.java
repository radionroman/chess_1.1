package com.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceType;

public class MoveGenerator {
    public MoveGenerator(){

    }


    public static List<Move> getLegalMovesForColor(GameState state) {
        PieceColor color = state.getTurnColor();
        List<Move> pseudoLegalMoves = getPseudoLegalMovesForColor(state);
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoLegalMoves) {
            GameState testState = state.copy();
            testState.movePiece(move);
            if (!testState.getBoard().isCheckPresent(color))
                legalMoves.add(move);
        }
        return legalMoves;
    }

    private static List<Move> getPseudoLegalMovesForColor(GameState state) {
        Board board = state.getBoard();
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> pieceMoves;
        List<PieceOnSquare> pieces = getPiecesOnSquare(state);
        for (PieceOnSquare pieceOnSquare : pieces) {
            pieceMoves = pieceOnSquare.getLegalMoves(board, state);

            if (pieceOnSquare.getPiece().getPieceType() == PieceType.PAWN) { // promotion and en passant checks
                moves.addAll(getEnPassantMoves(pieceOnSquare, state));

                if (!getPromotionMoves(pieceOnSquare, state).isEmpty()) {
                    moves.addAll(getPromotionMoves(pieceOnSquare, state));
                    continue;
                }
            }

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
        moves.add(new Move(from, new Square(captured.getRow() + direction, captured.getCol()), state.copy(),
                MoveType.EN_PASSANT));
        return moves;
    }
//TODO: fix castling
    private static List<Move> getCastlingMoves(PieceOnSquare pieceOnSquare, int rookCol, GameState state) {
        ArrayList<Move> moves = new ArrayList<>();
        PieceColor color = state.getTurnColor();
        Board board = state.getBoard();
        Piece king = pieceOnSquare.getPiece();
        if (king.hasMoved()) return moves;
        Square kingSquare = pieceOnSquare.getSquare();
        int kingCol = kingSquare.getCol();
        Square rookSquare = new Square(kingSquare.getRow(), rookCol);
        Piece rook = board.getPieceAt(rookSquare);
        if (rook == null || rook.hasMoved())return moves;
        int start, end;
        
        if (kingCol > rookCol) {
            start = rookCol+1;
            end = kingCol;
        } else {
            start = kingCol;
            end = rookCol-1;
        }
        // System.out.println(start + " " + end);
        for (int i = start; i <= end; i++){
            if (board.isCheckPresent(color, new Square(kingSquare.getRow(), i)) || (i != kingCol && !board.isEmptyAt(kingSquare.getRow(), i))) {
                return moves;
            }
        }
        if (kingCol < rookCol)moves.add(new Move(kingSquare, new Square(kingSquare.getRow(), end), state.copy(), MoveType.CASTLING_LONG));
        else moves.add(new Move(kingSquare, new Square(kingSquare.getRow(), end), state.copy(), MoveType.CASTLING_SHORT));
        System.out.println("Castling is possible");
        return moves;

    }

    private static List<Move> getPromotionMoves(PieceOnSquare pieceOnSquare, GameState state) {
        ArrayList<Move> moves = new ArrayList<>();
        Square from = pieceOnSquare.getSquare();
        if (from.getRow() != (pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? 1 : 6))
            return moves;
        int direction = pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? -1 : 1;
        Square to = new Square(from.getRow() + direction, from.getCol());
        moves.add(new Move(from, to, state.copy(), MoveType.PROMOTION_BISHOP));
        moves.add(new Move(from, to, state.copy(), MoveType.PROMOTION_ROOK));
        moves.add(new Move(from, to, state.copy(), MoveType.PROMOTION_KNIGHT));
        moves.add(new Move(from, to, state.copy(), MoveType.PROMOTION_QUEEN));
        return moves;

    }

    private static List<PieceOnSquare> getPiecesOnSquare(GameState state) {
        PieceColor color = state.getTurnColor();
        Board board = state.getBoard();
        ArrayList<PieceOnSquare> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.isEmptyAt(i,j))
                    continue;
                if (board.getPieceAt(i, j).getPieceColor() != color)
                    continue;
                pieces.add(new PieceOnSquare(board.getPieceAt(i, j), new Square(i, j)));
            }
        }
        return pieces;
    }
}
