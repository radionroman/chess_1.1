package com.chess.player;

import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.moves.Move;

public abstract class Player {
    
    public abstract void requestMove(ChessModel model, Consumer<Move> callback);
    public void onUserMove(Move move) {
        throw new IllegalAccessError("Only Humans can do this");
    }

    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
