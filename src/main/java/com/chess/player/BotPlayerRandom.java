package com.chess.player;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.chess.model.*;

public class BotPlayerRandom implements Player{
    Random random = new Random();

    public BotPlayerRandom(){
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        GameState gameState = model.getGameState();
        List<Move> legalMoves = gameState.getLegalMovesForColor(gameState.getTurnColor());
        Move move = legalMoves.get(random.nextInt(legalMoves.size()));
        callback.accept(move);
        
    }

}
