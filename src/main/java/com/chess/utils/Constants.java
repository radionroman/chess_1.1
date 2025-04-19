package com.chess.utils;

import com.chess.model.PieceColor;
import com.chess.model.PiecePlacement;
import com.chess.model.pieces.PieceType;

public class Constants {

    public static final int BOARD_ROWS = 8;
    public static final int BOARD_COLS = 8;

    public static final PiecePlacement[][] DEFAULT_BOARD = {
            {
                    new PiecePlacement(PieceType.ROOK, PieceColor.BLACK),
                    new PiecePlacement(PieceType.KNIGHT, PieceColor.BLACK),
                    new PiecePlacement(PieceType.BISHOP, PieceColor.BLACK),
                    new PiecePlacement(PieceType.QUEEN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.KING, PieceColor.BLACK),
                    new PiecePlacement(PieceType.BISHOP, PieceColor.BLACK),
                    new PiecePlacement(PieceType.KNIGHT, PieceColor.BLACK),
                    new PiecePlacement(PieceType.ROOK, PieceColor.BLACK),
            },
            {
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
                    new PiecePlacement(PieceType.PAWN, PieceColor.BLACK),
            },
            { null, null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null, null },
            {
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.PAWN, PieceColor.WHITE),
            },
            {
                    new PiecePlacement(PieceType.ROOK, PieceColor.WHITE),
                    new PiecePlacement(PieceType.KNIGHT, PieceColor.WHITE),
                    new PiecePlacement(PieceType.BISHOP, PieceColor.WHITE),
                    new PiecePlacement(PieceType.QUEEN, PieceColor.WHITE),
                    new PiecePlacement(PieceType.KING, PieceColor.WHITE),
                    new PiecePlacement(PieceType.BISHOP, PieceColor.WHITE),
                    new PiecePlacement(PieceType.KNIGHT, PieceColor.WHITE),
                    new PiecePlacement(PieceType.ROOK, PieceColor.WHITE),
            }

    };

}
