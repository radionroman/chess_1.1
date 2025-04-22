package com.chess.model;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
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

}
