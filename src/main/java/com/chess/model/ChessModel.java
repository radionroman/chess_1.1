package com.chess.model;

import java.util.*;
public class ChessModel {
    private Stack<Move> moveHistory = new Stack<>();
    private GameState gameState;
    public ChessModel(){
        gameState = new GameState();
    }
    public void processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        SelectionState selectionState = gameState.getSelectionState();
        if (selectionState.isPieceSelected()) {
            if (!clickedSquare.equals(selectionState.getSelectedSquare())){
                Move move = new Move(selectionState.getSelectedSquare(), clickedSquare, gameState.copy());
                moveHistory.push(move);
                System.out.println(move);
                gameState.movePiece(move); 
            }
            selectionState.setSelection(null); 
            gameState.setSelectablePieces();
        }
        else {
            selectionState.setSelection(clickedSquare);
            gameState.setMovesForSelectedPiece();
        }

        
    }

    public void undo(){
        Move previousMove;
        if (moveHistory.empty()) return;
        previousMove = moveHistory.pop();

        System.out.println("Undoing: " + previousMove);
        gameState = previousMove.getGameState();
    }

    public SteamrolledGameState getBoardState(){
        return new SteamrolledGameState(gameState.getIsSelectableSquares(), gameState.getBoard());
    }

   

}
