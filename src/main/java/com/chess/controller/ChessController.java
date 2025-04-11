package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.PieceColor;
import com.chess.player.BotPlayerMinimax;
import com.chess.player.BotPlayerRandom;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.ChessView;

public class ChessController {  
    private final ChessView view;
    private final ChessModel model;
    private HumanPlayer whitePlayer = new HumanPlayer();
    private Player blackPlayer = new BotPlayerMinimax();

    public ChessController( ChessModel model,ChessView view){
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row,col) -> {handleClickBoard(row, col);});
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getBoardState());
        nextTurn();
    }

    private void nextTurn(){

        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        current.requestMove(model, (move) -> {
            model.applyMove(move);
            
            view.refresh(model.getBoardState());
            if (model.getGameState().getLegalMovesForColor(model.getTurnColor()).isEmpty()) {
                System.out.println("MATE");
                view.mate(model.getTurnColor().toString());
                return;
            }
            nextTurn();
        });
    }

    public void userMoved(Move move){
        whitePlayer.onUserMove(move);
    }


    private void handleClickBoard(int row, int col){
        Move move = model.processBoardClicked(row, col);
        if (move != null) userMoved(move);

    }
    private void handleClickControl(String type){
        switch (type){
            case "Undo" -> {
                model.undo();
                model.undo();
                if (model.getTurnColor() == PieceColor.BLACK) nextTurn();
            }

            
            case "PVP" -> {
                whitePlayer = new HumanPlayer();
                blackPlayer = new HumanPlayer();

            }
            case "PVE_RANDOM_WHITE" -> {

                whitePlayer = new HumanPlayer();
                blackPlayer = new BotPlayerRandom();

            }
            case "PVE_RANDOM_BLACK" -> {

                whitePlayer = new HumanPlayer();
                blackPlayer = new BotPlayerRandom();

            }

            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getBoardState());
       
    }
    

    

}
