package com.chess.player;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.GameState;
import com.chess.model.Move;
import com.chess.model.MoveGenerator;
import com.chess.model.PieceColor;

public class BotPlayer implements Player{
    Random random = new Random();

    public BotPlayer(){
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        GameState gameState = model.getGameState();
        PieceColor turnColor = gameState.getTurnColor();
        List<Move> legalMoves = MoveGenerator.getLegalMovesForColor(gameState);
        Move move = legalMoves.get(random.nextInt(legalMoves.size()));
        callback.accept(move);
        
    }

}
