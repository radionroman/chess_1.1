package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.view.ChessView;

public class ChessController {  
    private final ChessView view;
    private final ChessModel model;

    public ChessController( ChessModel model,ChessView view){
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row,col) -> {handleClickBoard(row, col);});
        view.setControlClickedListener((type) -> handleClickControl(type));
        view.refresh(model.getBoardState());

        
    }

    private void handleClickBoard(int row, int col){
        model.processBoardClicked(row, col); 
        view.refresh(model.getBoardState());

    }
    private void handleClickControl(String type){
        System.out.println(type);
        switch (type){
            case "Undo" -> model.undo();
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        view.refresh(model.getBoardState());
       
    }
    

    

}
