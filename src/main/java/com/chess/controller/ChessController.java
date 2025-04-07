package com.chess.controller;

import com.chess.model.ChessModel;
import com.chess.view.ChessView;

public class ChessController {  
    private final ChessView view;
    private final ChessModel model;

    public ChessController( ChessModel model,ChessView view){
        this.model = model;
        this.view = view;
        view.setBoardClickedListeners((row,col) -> {handleClick(row, col);});
        view.refresh(model.getBoardState());
    }

    private void handleClick(int row, int col){
        System.out.println(row + " " + col);
        model.processBoardClicked(row, col); 
        view.refresh(model.getBoardState());
    }

    

}
