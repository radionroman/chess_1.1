package com.chess.model;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

import com.chess.model.moves.Move;
import com.chess.model.moves.MoveLogger;
import com.chess.model.moves.MoveValidator;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public class ChessModel {
    private final Stack<Move> moveStack = new Stack<>();
    private final GameState gameState;
    private final boolean[][] isSelectableSquares = new boolean[BOARD_ROWS][BOARD_COLS];
    private Optional<Square> selectedSquare = Optional.empty();

    public ChessModel() {
        gameState = new GameState();

    }

    public Move onSquareClicked(int row, int col) {
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

    public void executeMove(Move move) {
        MoveLogger.log(getTurnColor() + " moved: " +  move);
        gameState.executeMove(move);
        moveStack.push(move);

    }

    public void undoMove() {
        Move previousMove;
        if (moveStack.empty())
            return;
        previousMove = moveStack.pop();
        System.out.println("Undoing: " + previousMove);
        gameState.undoMove();
        clearSelectedSquare();

    }

    // Getters and setters
    public GameState getGameState() {
        return gameState;
    }

    public BoardSnapshot getBoardSnapshot() {
         return new BoardSnapshot(MoveValidator.getLegalMovesForColor(gameState), gameState.getBoard(),
                moveStack.empty() ? null : moveStack.peek(), selectedSquare);
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
