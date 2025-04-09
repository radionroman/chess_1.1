package com.chess.player;

import com.chess.model.ChessModel;
import com.chess.model.Move;

public interface Player {
    
    public void makeMove(Move move, ChessModel chessModel);
}
