package com.chess.model.moves;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.Board;
import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.Square;
import com.chess.model.pieces.King;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public class MoveValidator {
    public MoveValidator() {

    }


    public static List<Move> getLegalMovesForColor(GameState gameState) {
        GameState state = gameState.copy();
        // System.out.println(counter++);
        PieceColor color = state.getTurnColor();
        // long start = System.nanoTime();
        List<Move> pseudoLegalMoves = getPseudoLegalMovesForColor(state);
        // long end = System.nanoTime();
        // System.out.println(end - start);
        ArrayList<Move> legalMoves = new ArrayList<>();
        
        for (Move move : pseudoLegalMoves) {
            try {
                state.executeMove(move);
            } catch (Exception e) {
                
            }
            
            if (!isSquareAttacked(color, state, null))
                legalMoves.add(move);

            state.undoMove();
        }
        return legalMoves;
    }

    private static List<Move> getPseudoLegalMovesForColor(GameState state) {

        Board board = state.getBoard();
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> pieceMoves;
        // for (PieceOnSquare pieceOnSquare : pieces) {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (board.isEmptyAt(i, j))
                    continue;
                if (board.getPieceAt(i, j).getColor() != state.getTurnColor())
                    continue;

                pieceMoves = board.getPieceAt(i, j).getPseudoLegalMoves(state, i, j);

                moves.addAll(pieceMoves);

            }
        }

        return moves;
    }

    public static boolean isSquareAttacked(PieceColor color, GameState state, Square squareToCheck) {
        Board board = state.getBoard();

        List<Move> moves = new ArrayList<>();
        Square kingSquare = null;
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (board.isEmptyAt(i, j))
                    continue;
                if (board.getPieceAt(i, j).getPieceColor() == color) {

                    if (board.getPieceAt(i, j) instanceof King) {
                        kingSquare = new Square(i, j);
                    }
                    continue;

                }
                if (board.getPieceAt(i, j) instanceof King)
                    continue;

                moves.addAll(board.getPieceAt(i, j).getPseudoLegalMoves(state, i, j));
            }
        }

        if (squareToCheck != null)
            kingSquare = squareToCheck;
        for (Move move : moves) {
            if (move.getTo().equals(kingSquare))
                return true;
        }
        return false;
    }

}
