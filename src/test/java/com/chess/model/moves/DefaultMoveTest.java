package com.chess.model.moves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.chess.TestConstants.E2_SQUARE;
import static com.chess.TestConstants.E3_SQUARE;
import static com.chess.TestConstants.E4_SQUARE;
import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.pieces.Pawn;
import com.chess.model.pieces.Piece;

public class DefaultMoveTest {
    private Board board;
    private Piece whitePawn;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        board = new Board();
        whitePawn = new Pawn(PieceColor.WHITE);
        board.setPieceAt(E2_SQUARE, whitePawn);
    }

    @Test
    void testMakeMove() {
        var move = new DefaultMove(E2_SQUARE, E3_SQUARE);
        move.makeMove(board);

        assertTrue(whitePawn.hasMoved());
        assertNull(board.getPieceAt(E2_SQUARE));
        assertEquals(whitePawn, board.getPieceAt(E3_SQUARE));
        
    }

    @Test
    void testUnMakeMove() {
        var move = new DefaultMove(E2_SQUARE, E3_SQUARE);
        move.makeMove(board);
        move.unMakeMove(board);

        assertFalse(whitePawn.hasMoved());
        assertNull(board.getPieceAt(E3_SQUARE));
        assertEquals(whitePawn, board.getPieceAt(E2_SQUARE));
        
    }
    @Test
    void testUnMakeMoveHasMoved() {
        var move = new DefaultMove(E2_SQUARE, E3_SQUARE);
        move.makeMove(board);
        move = new DefaultMove(E3_SQUARE, E4_SQUARE);
        move.makeMove(board);
        move.unMakeMove(board);

        assertTrue(whitePawn.hasMoved());
        assertNull(board.getPieceAt(E4_SQUARE));
        assertEquals(whitePawn, board.getPieceAt(E3_SQUARE));
        
    }
}
