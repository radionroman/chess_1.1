package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.model.Move;
import com.chess.model.PieceColor;
import com.chess.player.HumanPlayer;
import com.chess.player.Player;
import com.chess.view.ChessView;

public class ChessController {  
    private final ChessView view;
    private final ChessModel model;
    private HumanPlayer whitePlayer = new HumanPlayer();
    private HumanPlayer blackPlayer = new HumanPlayer();
    // private Player blackPlayer = new BotPlayerMinimax();

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
        view.refresh(model.getBoardState());
        
    }
    private void handleClickControl(String type){
        Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
        switch (type){
            case "Undo" -> {
                model.undo();
                model.undo();
            }

            
            
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getBoardState());
       
    }
    

    

}
