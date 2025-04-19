package com.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;
import static com.chess.utils.Constants.*;

public class Board {
    Piece[][] board;

    public Board() {
        board = new Piece[BOARD_ROWS][BOARD_COLS];
    }

    public void setup(PiecePlacement[][] piecePlacements) {

        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (piecePlacements[i][j] != null)
                    this.setPieceAt(i, j,
                            PieceFactory.createPiece(piecePlacements[i][j].type(), piecePlacements[i][j].color()));
            }
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
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (!this.isEmptyAt(i, j))
                    copyBoard.setPieceAt(i, j, this.getPieceAt(i, j).copy());
                else
                    copyBoard.setPieceAt(i, j, null);
            }
        }
        return copyBoard;
    }

    public boolean isEmptyAt(int row, int col) {
        return board[row][col] == null;
    }

    public boolean isEmptyAt(Square square) {
        return board[square.getRow()][square.getCol()] == null;
    }

    // Game rules
    public boolean isCheckPresent(PieceColor color) {
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
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (this.isEmptyAt(i, j))
                    continue;
                if (this.getPieceAt(i, j).getPieceColor() != color)
                    continue;
                pieces.add(new PieceOnSquare(this.getPieceAt(i, j), new Square(i, j)));
            }
        }
        return pieces;
    }

}
