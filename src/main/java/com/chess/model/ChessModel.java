package com.chess.model;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ChessModel {
    private final Stack<Move> moveHistory = new Stack<>();
    private GameState gameState;
    private final boolean[][] isSelectableSquares = new boolean[8][8];
    private Optional<Square> selectedSquare = Optional.empty();

    public ChessModel() {
        gameState = new GameState();

    }

    public Move processBoardClicked(int row, int col) {
        Square clickedSquare = new Square(row, col);
        Move move = null;
        List<Move> moves;

        if (selectedSquare.isPresent()) {

            if (!clickedSquare.equals(selectedSquare.get())) {

                moves = gameState.getLegalMovesForColor(gameState.getTurnColor());

                for (Move legalMove : moves) {
                    if (legalMove.getFrom().equals(selectedSquare.get()) && legalMove.getTo().equals(clickedSquare)) {
                        move = legalMove;
                    }
                }
            }


            clearSelectedSquare();


        } else {
            setSelectedSquare(clickedSquare);
        }
        return move;

    }

    public void applyMove(Move move) {
        gameState.movePiece(move);
        moveHistory.push(move);
    }



    public void undo() {
        Move previousMove;
        if (moveHistory.empty())
            return;
        previousMove = moveHistory.pop();
        System.out.println("Undoing: " + previousMove);
        this.gameState = previousMove.getGameState().copy();
        clearSelectedSquare();

    }


    // Getters and setters
    public GameState getGameState() {
        return gameState;
    }

    public RenderState getRenderState(boolean allowClicks) {

        return new RenderState(gameState.getLegalMovesForColor(gameState.getTurnColor()), gameState.getBoard(),
                moveHistory.empty() ? null : moveHistory.peek(), selectedSquare, allowClicks);
    }

    public Optional<Square> getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(Square square) {
        selectedSquare = Optional.of(square);
    }

    public void clearSelectedSquare() {
        selectedSquare = Optional.empty();
    }

    public boolean[][] getIsSelectableSquares() {
        return isSelectableSquares;
    }
    public PieceColor getTurnColor() {
        return gameState.getTurnColor();
    }
}
