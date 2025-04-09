package com.chess.model;

import java.util.*;

import com.chess.controller.ChessController;
import com.chess.model.pieces.Piece;
public class ChessModel {
    private Stack<Move> moveHistory = new Stack<>();
    private GameState gameState;
    private ChessController controller;
    public ChessModel(){
        gameState = new GameState();
        this.controller = controller;
    }

    public void setController(ChessController controller ){
        this.controller = controller;
    }

    public void processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        Optional<Square> selectedSquare = gameState.getSelectedSquare();
        if (selectedSquare.isPresent()) {
            if (!clickedSquare.equals(selectedSquare.get())){
                Move move = new Move(selectedSquare.get(), clickedSquare, gameState.copy());
                moveHistory.push(move);
                System.out.println(move);
                controller.userMoved(move);
                
            }
            gameState.clearSelectedSquare(); 
            gameState.setSelectablePieces();
        }
        else {
            gameState.setSelectedSquare(clickedSquare);
            gameState.setMovesForSelectedPiece();
        }

        
    }

    public void applyMove(Move move){
        gameState.movePiece(move);
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
        return new RenderState(gameState.getIsSelectableSquares(), gameState.getBoard());
    }

   

}
