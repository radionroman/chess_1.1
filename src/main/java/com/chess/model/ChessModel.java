package com.chess.model;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public class ChessModel {
    private final Stack<Move> moveHistory = new Stack<>();
    private final GameState gameState;
    private final boolean[][] isSelectableSquares = new boolean[BOARD_ROWS][BOARD_COLS];
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

                moves = MoveValidator.getLegalMovesForColor(gameState);

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
        
        gameState.makeMove(move);
        moveHistory.push(move);
    }

    public void undo() {
        Move previousMove;
        if (moveHistory.empty())
            return;
        previousMove = moveHistory.pop();
        System.out.println("Undoing: " + previousMove);
        gameState.undoMove();
        clearSelectedSquare();

    }

    // Getters and setters
    public GameState getGameState() {
        return gameState;
    }

    public RenderState getRenderState() {
         return new RenderState(MoveValidator.getLegalMovesForColor(gameState), gameState.getBoard(),
                moveHistory.empty() ? null : moveHistory.peek(), selectedSquare);
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
