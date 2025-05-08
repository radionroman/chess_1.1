package com.chess.model.moves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.chess.TestConstants.E7_SQUARE;
import static com.chess.TestConstants.E8_SQUARE;
import com.chess.model.Board;
import com.chess.model.PieceColor;
import com.chess.model.pieces.Pawn;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceType;

public class PromotionMoveTest {
    private Board board;
    private Piece whitePawn;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        board = new Board();
        whitePawn = new Pawn(PieceColor.WHITE);
        board.setPieceAt(E7_SQUARE, whitePawn);
    }

    
    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"QUEEN", "ROOK", "BISHOP", "KNIGHT"}) 
    void testMakePromotion(PieceType promotionType) {
        var move = new PromotionMove(E7_SQUARE, E8_SQUARE, promotionType);
        move.makeMove(board);

        assertNull(board.getPieceAt(E7_SQUARE), "Cool");
        assertEquals(promotionType, board.getPieceAt(E8_SQUARE).getPieceType());
        assertTrue(board.getPieceAt(E8_SQUARE).hasMoved());
        
    }

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"QUEEN", "ROOK", "BISHOP", "KNIGHT"}) 
    void testUnMakePromotion(PieceType promotionType) {
        var move = new PromotionMove(E7_SQUARE, E8_SQUARE, promotionType);
        move.makeMove(board);
        move.unMakeMove(board);

        assertNull(board.getPieceAt(E8_SQUARE));
        assertEquals(whitePawn, board.getPieceAt(E7_SQUARE));
        
    }


}
