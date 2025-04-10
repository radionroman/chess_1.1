package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.PieceColor;
import com.chess.player.*;
import com.chess.view.ChessView;

public class ChessController {  
    private final ChessView view;
    private final ChessModel model;
    private HumanPlayer whitePlayer;
    private Player blackPlayer;

    public ChessController( ChessModel model,ChessView view){
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row,col) -> {handleClickBoard(row, col);});
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getBoardState());
        whitePlayer = new HumanPlayer();
        blackPlayer = new BotPlayer();
        nextTurn();
    }

    private void nextTurn(){
        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        System.out.println("Next turn called " + model.getTurnColor());
        current.requestMove(model, (move) -> {
            model.applyMove(move);
            view.refresh(model.getBoardState());
            nextTurn();
        });
    }

    public void userMoved(Move move){
        whitePlayer.onUserMove(move);
    }


    private void handleClickBoard(int row, int col){
        Move move = model.processBoardClicked(row, col);
        if(move != null)whitePlayer.onUserMove(move);
        view.refresh(model.getBoardState());

    }
    private void handleClickControl(String type){
        switch (type){
            case "Undo" -> {
                model.undo();
            }

            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getBoardState());
       
    }
    

    

}
