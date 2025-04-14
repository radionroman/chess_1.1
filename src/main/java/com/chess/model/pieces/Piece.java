package com.chess.model.pieces;

import java.util.List;

import com.chess.model.*;

public abstract class Piece {
    private final PieceColor color;
    private final PieceType type;
    private String symbol;
    private int rank;

    public Piece(PieceColor color, PieceType type, int rank) {
        this.color = color;
        this.type = type;
        this.rank = rank;
    }

    public void setChar(String c) {
        symbol = c;
    }

    public PieceType getPieceType() {
        return type;
    }

    public Piece copy() {
        return PieceFactory.createPiece(type, color);
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

    public abstract List<Square> getLegalMoves(Piece[][] board, int row, int col);

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
