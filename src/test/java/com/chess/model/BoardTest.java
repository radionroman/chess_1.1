package com.chess.model;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void testGetPieceAt() {

    }

    @Test
    void testGetPieceAt2() {

    }

    @Test
    void testIsEmptyAt() {

    }

    @Test
    void testIsEmptyAt2() {

    }

    @Test
    void testSetPieceAt() {

    }

    @Test
    void testSetPieceAt2() {

    }

    @Test
    void testSetUp() {

    }

    @Test
    void testSetup() {
        Board board = new Board();
        board.setup("8/8/8/8/8/8/8/8");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.err.println(board.getPieceAt(i, j));
            }
        }

    }
}
