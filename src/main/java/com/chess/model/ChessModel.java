package com.chess.model;

import java.util.*;

import com.chess.model.pieces.Piece;
public class ChessModel {
    private Stack<Move> moveHistory = new Stack<>();
    private GameState gameState;
    private boolean[][] isSelectableSquares= new boolean[8][8];
    private Optional<Square> selectedSquare = Optional.empty();
    public ChessModel(){
        gameState = new GameState();
        
    }

    public Move processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        Move move = null;
        if (selectedSquare.isPresent()) {
            if (!clickedSquare.equals(selectedSquare.get())){
                Piece[][] board = gameState.getBoard();
                Piece piece = board[selectedSquare.get().getRow()][selectedSquare.get().getCol()];
                if (piece == null) return move;
                move = new Move(selectedSquare.get(), clickedSquare, gameState.copy());
            }
            clearSelectedSquare();
        }
        else {
            setSelectedSquare(clickedSquare);
        }
        return move;
        
    }
    public void applyMove(Move move){
        gameState.movePiece(move);
        moveHistory.push(move);
    }

    public PieceColor getTurnColor(){
        return gameState.getTurnColor();
    }

    public void undo(){
        Move previousMove;
        if (moveHistory.empty()) return;
        previousMove = moveHistory.pop();
        System.out.println("Undoing: " + previousMove);
        gameState = previousMove.getGameState();

    }

    public GameState getGameState(){
        return gameState;
    }

    public RenderState getBoardState(){
        return new RenderState(gameState.getLegalMovesForColor(gameState.getTurnColor()), gameState.getBoard(), moveHistory.empty() ? null : moveHistory.peek(), selectedSquare);
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

    

}
