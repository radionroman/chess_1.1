package com.chess.model;

import java.util.*;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;

public class GameState {
    private Piece[][] board;
    private boolean[][] isSelectableSquares= new boolean[8][8];
    private PieceColor turnColor;
    private Optional<Square> selectedSquare = Optional.empty();

    public GameState() {
        board = boardInit();
        turnColor = PieceColor.WHITE;
        setSelectablePieces();
    }

    public GameState(Piece[][] board, PieceColor turnColor) {
        this.board = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone();
        }
        this.turnColor = turnColor;
        setSelectablePieces();
    }

    public GameState copy() {
        GameState gameStateCopy = new GameState(board, turnColor);
        return gameStateCopy;
    }



    private Piece[][] boardInit(){
        Piece[][] board = new Piece[8][8];
        PieceType[] order = new PieceType[]{
            PieceType.ROOK, 
            PieceType.KNIGHT, 
            PieceType.BISHOP, 
            PieceType.QUEEN, 
            PieceType.KING, 
            PieceType.BISHOP, 
            PieceType.KNIGHT, 
            PieceType.ROOK, 
        };
        for (int i = 0; i < board.length; i++) {
            board[0][i] = PieceFactory.createPiece(order[7-i], PieceColor.BLACK);
            board[1][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.BLACK);
            board[6][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.WHITE);
            board[7][i] = PieceFactory.createPiece(order[i], PieceColor.WHITE);
        }
        return board;
    }

    public void setSelectablePieces(){
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
    public void setMovesForSelectedPiece(){
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

    public void movePiece(Move move){

        Square from = move.getFrom();
        Square to = move.getTo();
        Piece piece = board[from.getRow()][from.getCol()];
        if (!piece.getLegalMoves(board, from.getRow(), from.getCol()).contains(to)){
            System.out.println("Illegal move attempted!");
            return;
        }
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;
        if (turnColor == PieceColor.WHITE) turnColor = PieceColor.BLACK;
        else turnColor = PieceColor.WHITE; 
        
    }



    public Piece[][] getBoard() {
        return board;
    }



    public boolean[][] getIsSelectableSquares() {
        return isSelectableSquares;
    }



    public PieceColor getTurnColor() {
        return turnColor;
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
    

}
