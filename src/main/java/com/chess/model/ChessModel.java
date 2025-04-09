package com.chess.model;

import java.util.*;

import com.chess.model.pieces.*;
public class ChessModel {



    private Piece[][] board;
    private boolean[][] isSelectableSquares;
    private PieceColor turnColor;
    private SelectionState choiceState; 
    private Queue<Move> moveHistory = new LinkedList<>();
    public ChessModel(){
        this.board = boardInit();
        isSelectableSquares = new boolean[8][8];
        turnColor = PieceColor.WHITE;
        choiceState = new SelectionState();
        setSelectablePieces();
    }


    public ChessModel clone(){
        return new ChessModel();
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

    public void processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        if (choiceState.isPieceSelected()) {
            if (!clickedSquare.equals(choiceState.getSelectedSquare())){
                Move move = new Move(choiceState.getSelectedSquare(), clickedSquare);
                moveHistory.add(move);
                System.out.println(move);
                movePiece(move); 
            }
            choiceState.setSelection(null); 
            setSelectablePieces();
        }
        else {
            choiceState.setSelection(clickedSquare);
            setMovesForSelectedPiece();
        }

        
    }

    public void undo(){
        System.out.println("undo(cant)");
    }

    public SteamrolledGameState getBoardState(){
        return new SteamrolledGameState(isSelectableSquares, board);
    }

    private void setSelectablePieces(){
        if (!choiceState.isPieceSelected()){
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
    private void setMovesForSelectedPiece(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                
                isSelectableSquares[i][j] = false;

            }
        }
        isSelectableSquares[choiceState.getSelectedSquare().getRow()][choiceState.getSelectedSquare().getCol()] = true;
        List<Square> legalMoves = board[choiceState.getSelectedSquare().getRow()][choiceState.getSelectedSquare().getCol()].getLegalMoves(board, choiceState.getSelectedSquare().getRow(), choiceState.getSelectedSquare().getCol());
        for (Square square : legalMoves) {
            isSelectableSquares[square.getRow()][square.getCol()] = true;
        }
    }

    private void movePiece(Move move){

        Square from = move.getFrom();
        Square to = move.getTo();
        Piece piece = board[from.getRow()][from.getCol()];
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;
        if (turnColor == PieceColor.WHITE) turnColor = PieceColor.BLACK;
        else turnColor = PieceColor.WHITE; 
    }

}
