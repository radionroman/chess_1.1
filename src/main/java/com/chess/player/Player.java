package com.chess.player;

import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.Move;

public interface Player {
    
    public void requestMove(ChessModel model, Consumer<Move> callback);
}
