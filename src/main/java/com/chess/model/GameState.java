package com.chess.model;

import java.util.*;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;

public class GameState {
    private Piece[][] board;
    private PieceColor turnColor;


    public GameState() {
        board = boardInit();
        turnColor = PieceColor.WHITE;
    }

    public GameState(Piece[][] board, PieceColor turnColor) {
        this.board = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.board[i][j] = board[i][j] == null ? null : board[i][j].copy();
            }
        }
        this.turnColor = turnColor;
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


    public void movePiece(Move move){

        Square from = move.getFrom();
        Square to = move.getTo();
        Piece piece = board[from.getRow()][from.getCol()];
        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;
        if (turnColor == PieceColor.WHITE) turnColor = PieceColor.BLACK;
        else turnColor = PieceColor.WHITE; 
        if(isCheckPresent(turnColor)){
            System.out.println("The " + turnColor + "king is in check!");
            processCheck();
        }
        
    }


    // Game rules
    private boolean isCheckPresent(PieceColor color){
        PieceOnSquare king = null;
        List<PieceOnSquare> myPieces = getPiecesOnSquare(color);
        for (PieceOnSquare pieceOnSquare : myPieces) {
            if (pieceOnSquare.getPiece().getPieceType() == PieceType.KING) {
                king = pieceOnSquare;
            }
        }
        List<PieceOnSquare> enemyPieces = getPiecesOnSquare(color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        for (PieceOnSquare pieceOnSquare : enemyPieces) {
            if (pieceOnSquare.getLegalMovesTo(board).contains(king.getSquare())) return true;
        }
        return false;
    }

    private void processCheck() {

    }

    // Basic Getters
    public Piece[][] getBoard() {
        return board;
    }


    public PieceColor getTurnColor() {
        return turnColor;
    }

    // Advanced getters
    public List<PieceOnSquare> getPiecesOnSquare(PieceColor color) {
        ArrayList<PieceOnSquare> pieces = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) continue;
                if (board[i][j].getPieceColor() != color) continue;
                pieces.add(new PieceOnSquare(board[i][j], new Square(i, j)));
            }
        }
        return pieces;
    }

    public List<Move> getPseudoLegalMovesForColor(PieceColor color) {
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> pieceMoves = null;
        List<PieceOnSquare> pieces = getPiecesOnSquare(color);
        for (PieceOnSquare pieceOnSquare : pieces) {
            pieceMoves = pieceOnSquare.getLegalMoves(board, this);
            for (Move pieceMove: pieceMoves) {
                moves.add(pieceMove);
            }
        }
        return moves;
    }

    public List<Move> getLegalMovesForColor(PieceColor color) {
        List<Move> pseudoLegalMoves = getPseudoLegalMovesForColor(color);
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoLegalMoves) {
            GameState testState = this.copy();
            testState.movePiece(move);
            if(!testState.isCheckPresent(color)) legalMoves.add(move);
        }

        return legalMoves;
    }







}
