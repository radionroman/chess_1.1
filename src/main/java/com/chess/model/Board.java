package com.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;

public class Board {
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public void init() {
        PieceType[] order = new PieceType[] {
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.QUEEN,
                PieceType.KING,
                PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK,
        };
        for (int i = 0; i < 8; i++) {
            board[0][i] = PieceFactory.createPiece(order[i], PieceColor.BLACK);
            board[1][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.BLACK);
            board[6][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.WHITE);
            board[7][i] = PieceFactory.createPiece(order[i], PieceColor.WHITE);
        }

        
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }
    public Piece getPieceAt(Square square) {
        return board[square.getRow()][square.getCol()];
    }

    public void setPieceAt(int row, int col, Piece piece) {
        this.board[row][col] = piece;
    }
    public void setPieceAt(Square square, Piece piece) {
        this.board[square.getRow()][square.getCol()] = piece;
    }

    public Board copy() {
        Board copyBoard = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!this.isEmptyAt(i, j))copyBoard.setPieceAt(i, j, this.getPieceAt(i, j).copy());
                else copyBoard.setPieceAt(i, j, null);
            }
        }
        return copyBoard;
    }
    public void movePiece(Move move, PieceColor turnColor) {

        Square from = move.getFrom();
        Square to = move.getTo();
        Piece piece = board[from.getRow()][from.getCol()];
        MoveType type = move.getType();
        switch (type) {
            case DEFAULT -> {
                board[to.getRow()][to.getCol()] = piece;
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_QUEEN -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.QUEEN, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_BISHOP -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.BISHOP, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_ROOK -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.ROOK, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_KNIGHT -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.KNIGHT, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case CASTLING_LONG -> {
                board[from.getRow()][from.getCol()] = null;
                board[to.getRow()][to.getCol()] = piece;
                board[to.getRow()][to.getCol() - 1] = board[to.getRow()][to.getCol() + 1];
                board[to.getRow()][to.getCol() + 1] = null;
            }
            case CASTLING_SHORT -> {
                board[from.getRow()][from.getCol()] = null;
                board[to.getRow()][to.getCol()] = piece;
                board[to.getRow()][to.getCol() + 1] = board[to.getRow()][to.getCol() - 1];
                board[to.getRow()][to.getCol() - 1] = null;
            }
            case EN_PASSANT -> {
                board[from.getRow()][from.getCol()] = null;
                board[from.getRow()][to.getCol()] = null; // capture
                board[to.getRow()][to.getCol()] = piece;
            }
            default -> {
                throw new IllegalArgumentException("No such move type supported: " + type.toString());
            }
            
        }
        board[to.getRow()][to.getCol()].hasMoved();
    }

    public boolean isEmptyAt(int row, int col) {
        return board[row][col] == null;
    }
    public boolean isEmptyAt(Square square) {
        return board[square.getRow()][square.getCol()] == null;
    }
    // Game rules
    public  boolean isCheckPresent(PieceColor color) {
        PieceOnSquare king = null;
        List<PieceOnSquare> myPieces = getPiecesOnSquare(color);
        for (PieceOnSquare pieceOnSquare : myPieces) {
            if (pieceOnSquare.getPiece().getPieceType() == PieceType.KING) {
                king = pieceOnSquare;
            }
        }
        List<PieceOnSquare> enemyPieces = getPiecesOnSquare(
                color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        if (king == null)
            return false;
        for (PieceOnSquare pieceOnSquare : enemyPieces) {
            if (pieceOnSquare.getLegalMovesTo(this).contains(king.getSquare()))
                return true;
        }
        return false;
    }
    public boolean isCheckPresent(PieceColor color, Square square) {

        List<PieceOnSquare> enemyPieces = getPiecesOnSquare(
                color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        for (PieceOnSquare pieceOnSquare : enemyPieces) {
            if (pieceOnSquare.getLegalMovesTo(this).contains(square))
                return true;
        }
        return false;
    }

    public List<PieceOnSquare> getPiecesOnSquare(PieceColor color) {
        ArrayList<PieceOnSquare> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.isEmptyAt(i,j))
                    continue;
                if (this.getPieceAt(i, j).getPieceColor() != color)
                    continue;
                pieces.add(new PieceOnSquare(this.getPieceAt(i, j), new Square(i, j)));
            }
        }
        return pieces;
    }

    
}
