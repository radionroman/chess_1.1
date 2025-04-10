package com.chess.player;

import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.Move;

public class HumanPlayer implements Player {
    private Consumer<Move> callback;
    public HumanPlayer(){
    }

    public void onUserMove(Move move){
        if (callback != null) {
            callback.accept(move);
        }
        
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        this.callback = callback;
    }

   

}
