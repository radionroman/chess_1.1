package com.chess.model.pieces;

import java.util.List;

import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.Square;

public abstract class Piece {
    private final PieceColor color;
    private final PieceType type;
    private String symbol;
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

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setChar(String c) {
        symbol = c;
    }

    public PieceType getPieceType() {
        return type;
    }

    public Piece copy() {
        Piece copyPiece = PieceFactory.createPiece(type, color);
        if (this.hasMoved)copyPiece.setHasMoved();
        return copyPiece;
    }

    public String getChar() {
        return symbol;
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

    public abstract List<Square> getLegalMoves(Board board, int row, int col);

    protected boolean isInsideBoard(int r, int c) {
        return r >= 0 && c >= 0 && r < 8 && c < 8;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getRank() {
        return rank;
    }

}
