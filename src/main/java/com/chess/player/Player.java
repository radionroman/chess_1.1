package com.chess.player;

import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.Move;

public abstract class Player {
    
    public abstract void requestMove(ChessModel model, Consumer<Move> callback);
    public void onUserMove(Move move) {
        throw new IllegalAccessError("Only Humans can do this");
    }
}
