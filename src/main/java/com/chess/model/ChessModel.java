package com.chess.model;

import com.chess.model.pieces.*;
public class ChessModel {

    private Piece[][] board;
    private boolean[][] isActiveSquares;
    private PieceColor turnColor;
    private ChoiceState choiceState;

    public ChessModel(){
        this.board = boardInit();
        isActiveSquares = new boolean[8][8];
        turnColor = PieceColor.WHITE;
        setActiveSquares();
        choiceState = new ChoiceState();
    }

    private Piece[][] boardInit(){
        Piece[][] board = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            board[1][i] = new Pawn(PieceColor.WHITE);
            board[6][i] = new Pawn(PieceColor.BLACK);
         }
        return board;
    }

    public void processBoardClicked(int row, int col){
        Square clickedSquare = new Square(row, col);
        if (choiceState.isPieceChosen()) {
            if (!clickedSquare.isEqual(choiceState.getChosenSquare())){
                movePiece(choiceState.getChosenSquare(), clickedSquare);
            }
            choiceState.setChoice(null);
        }
        else {
            choiceState.setChoice(clickedSquare);
        }
    }

    public BoardState getBoardState(){
        

        return new BoardState(isActiveSquares, board);
    }

    private void setActiveSquares() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null ) {
                    isActiveSquares[i][j] = false;
                    continue;
                }
                if (board[i][j].getPieceColor() == PieceColor.WHITE) isActiveSquares[i][j] = true;
            }
        }
    }

}
