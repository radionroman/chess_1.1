package com.chess.model.pieces;

import java.util.List;

import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.moves.Move;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public abstract class Piece {
    private final PieceColor color;
    private final PieceType type;
    private final int rank;
    private boolean hasMoved = false;

    public Piece(PieceColor color, PieceType type, int rank) {
        this.color = color;
        this.type = type;
        this.rank = rank;
    }

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public void setHasNotMoved() {
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public PieceType getPieceType() {
        return type;
    }

    public String getUnicodeSymbol() {
        return switch (this.getClass().getSimpleName()) {
            case "Pawn" -> getPieceColor() == PieceColor.WHITE ? "♙" : "♟";
            case "Rook" -> getPieceColor() == PieceColor.WHITE ? "♖" : "♜";
            case "Knight" -> getPieceColor() == PieceColor.WHITE ? "♘" : "♞";
            case "Bishop" -> getPieceColor() == PieceColor.WHITE ? "♗" : "♝";
            case "Queen" -> getPieceColor() == PieceColor.WHITE ? "♕" : "♛";
            case "King" -> getPieceColor() == PieceColor.WHITE ? "♔" : "♚";
            default -> " ";
        };
    }

    public PieceColor getPieceColor() {
        return color;
    }
    public Piece copy() {
        Piece clone = PieceFactory.createPiece(this.getPieceType(), this.getPieceColor());
        if (this.hasMoved) {
            clone.setHasMoved();
        }
        return clone;
    }

    public abstract List<Move> getPseudoLegalMoves(GameState state, int row, int col);

    protected boolean isInsideBoard(int r, int c) {
        return r >= 0 && c >= 0 && r < BOARD_ROWS && c < BOARD_COLS;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public int getRank() {
        return rank;
    }

}
