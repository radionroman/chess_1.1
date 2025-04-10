package com.chess.model;

import java.util.*;

import com.chess.controller.ChessController;
import com.chess.model.pieces.Piece;
public class ChessModel {
    private Stack<Move> moveHistory = new Stack<>();
    private GameState gameState;
    private boolean[][] isSelectableSquares= new boolean[8][8];
    private Optional<Square> selectedSquare = Optional.empty();
    public ChessModel(){
        gameState = new GameState();
        setSelectablePieces(gameState);
    }

    public Move processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        Move move = null;
        if (selectedSquare.isPresent()) {
            if (!clickedSquare.equals(selectedSquare.get())){
                Piece[][] board = gameState.getBoard();
                Piece piece = board[selectedSquare.get().getRow()][selectedSquare.get().getCol()];
                if (piece == null) return move;
                System.out.println(piece.getLegalMoves(board, row, col));
                if (!piece.getLegalMoves(board, selectedSquare.get().getRow(), selectedSquare.get().getCol()).contains(clickedSquare)) return move;
                move = new Move(selectedSquare.get(), clickedSquare, gameState.copy());
                moveHistory.push(move);
                
                
            }
            clearSelectedSquare();
            setSelectablePieces(gameState);
        }
        else {
            setSelectedSquare(clickedSquare);
            setMovesForSelectedPiece(gameState);
        }
        return move;
        
    }

    public void applyMove(Move move){
        gameState.movePiece(move);
        setSelectablePieces(gameState );
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
        return new RenderState(isSelectableSquares, gameState.getBoard());
    }

    public void setSelectablePieces(GameState gameState){
        Piece[][] board = gameState.getBoard();
        PieceColor turnColor = gameState.getTurnColor();
        if (!selectedSquare.isPresent()){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == null ) {
                        isSelectableSquares[i][j] = false;
                        continue;
                    }
                    isSelectableSquares[i][j] = board[i][j].getPieceColor() == turnColor;
                    
                }
            }
        }
    }

    public void setMovesForSelectedPiece(GameState gameState){
        Piece[][] board = gameState.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                
                isSelectableSquares[i][j] = false;

            }
        }
        isSelectableSquares[selectedSquare.get().getRow()][selectedSquare.get().getCol()] = true;
        List<Square> legalMoves = board[selectedSquare.get().getRow()][selectedSquare.get().getCol()].getLegalMoves(board, selectedSquare.get().getRow(), selectedSquare.get().getCol());
        for (Square square : legalMoves) {
            isSelectableSquares[square.getRow()][square.getCol()] = true;
        }
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
