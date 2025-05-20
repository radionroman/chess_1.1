package com.chess.model;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

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

    public boolean isEmptyAt(int row, int col) {
        return board[row][col] == null;
    }

    public boolean isEmptyAt(Square square) {
        return board[square.getRow()][square.getCol()] == null;
    }

    public Board copy() {
        Board b2 = new Board();
        for (int r = 0; r < BOARD_ROWS; r++) {
        for (int c = 0; c < BOARD_COLS; c++) {
            Piece p = this.getPieceAt(r,c);
            if (p != null) b2.setPieceAt(r,c, p.copy()); 
        }
        }
        return b2;
    }

    public void setup(String FENString) {
        int row = 0;
        int col = 0;
        for (char c : FENString.toCharArray()) {
            if (c == '/') {
                row++;
                col = 0;
            }
            else if (c <= '9' && c >= '0') {
                col += c - '0' - 1;

            }
            else {
                switch (c) {
                    // Black pieces
                    case 'p' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.PAWN,   PieceColor.BLACK));
                    case 'r' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.ROOK,   PieceColor.BLACK));
                    case 'n' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.KNIGHT, PieceColor.BLACK));
                    case 'b' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.BISHOP, PieceColor.BLACK));
                    case 'q' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.QUEEN,  PieceColor.BLACK));
                    case 'k' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.KING,   PieceColor.BLACK));

                    // White pieces
                    case 'P' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.PAWN,   PieceColor.WHITE));
                    case 'R' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.ROOK,   PieceColor.WHITE));
                    case 'N' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.KNIGHT, PieceColor.WHITE));
                    case 'B' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.BISHOP, PieceColor.WHITE));
                    case 'Q' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.QUEEN,  PieceColor.WHITE));
                    case 'K' -> setPieceAt(row, col, PieceFactory.createPiece(PieceType.KING,   PieceColor.WHITE));

                }
                col++;
            }
        }
    }

}
