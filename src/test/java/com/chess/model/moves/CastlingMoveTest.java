package com.chess.model.moves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.Square;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;


public class CastlingMoveTest {
    private Board board;
    private Piece whiteKing, whiteRook;
    private Square from = new Square(7, 4);
    private Square to = new Square(7, 2);

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        board = new Board();
        whiteKing = PieceFactory.createPiece(PieceType.KING, PieceColor.WHITE);
        whiteRook = PieceFactory.createPiece(PieceType.ROOK, PieceColor.WHITE);
        board.setPieceAt(from, whiteKing);
        board.setPieceAt(7,0, whiteRook);
    }

    @Test
    void testExecute() {
        var move = new CastlingMove(from, to, true);
        move.execute(board);
        assertEquals(whiteRook, board.getPieceAt(7, 3));
        assertEquals(whiteKing, board.getPieceAt(to));
        assertEquals(null, board.getPieceAt(from));
        
        
    }

    // @Test
    // void testUndo() {
    //     var move = new DefaultMove(E2_SQUARE, E3_SQUARE);
    //     move.execute(board);
    //     move.undo(board);

    //     assertFalse(whitePawn.hasMoved());
    //     assertNull(board.getPieceAt(E3_SQUARE));
    //     assertEquals(whitePawn, board.getPieceAt(E2_SQUARE));
        
    // }
}
