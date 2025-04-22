package com.chess.player;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.GameState;
import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;

public class BotPlayerRandom extends Player {
    Random random = new Random();

    public BotPlayerRandom() {
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        GameState gameState = model.getGameState();
        List<Move> legalMoves = MoveValidator.getLegalMovesForColor(gameState);
        Move move = legalMoves.get(random.nextInt(legalMoves.size()));
        callback.accept(move);

    }

}
