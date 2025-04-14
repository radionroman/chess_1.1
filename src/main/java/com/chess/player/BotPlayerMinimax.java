package com.chess.player;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.chess.model.ChessModel;
import com.chess.model.GameState;
import com.chess.model.Move;
import com.chess.model.PieceColor;

public class BotPlayerMinimax implements Player{
    Random random = new Random();

    public BotPlayerMinimax(){
    }

    private int evaluate(GameState gameState) {
        return gameState.getLegalMovesForColor(PieceColor.BLACK).size() - gameState.getLegalMovesForColor(PieceColor.WHITE).size();
    }

    private int minimax(GameState gameState, int depth, boolean maximizingPlayer) {
        if (depth == 0 || gameState.getLegalMovesForColor(gameState.getTurnColor()).isEmpty()) {
            return evaluate(gameState);
        }
    
        List<Move> legalMoves = gameState.getLegalMovesForColor(gameState.getTurnColor());
    
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                GameState child = gameState.copy();
                child.movePiece(move);

                int eval = minimax(child, depth - 1, false); // switch to minimizing
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                GameState child = gameState.copy();
                child.movePiece(move);
                int eval = minimax(child, depth - 1, true); // switch to maximizing
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
    
    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        GameState gameState = model.getGameState();
        List<Move> legalMoves = gameState.getLegalMovesForColor(gameState.getTurnColor());
    
        Move bestMove = null;
        int bestValue = (gameState.getTurnColor() == PieceColor.WHITE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    
        for (Move move : legalMoves) {
            GameState child = gameState.copy();
            child.movePiece(move);
            int value = minimax(child, 2, gameState.getTurnColor() == PieceColor.BLACK); // depth 3 is good for start
    
            if (gameState.getTurnColor() == PieceColor.WHITE && value > bestValue) {
                bestValue = value;
                bestMove = move;
            } else if (gameState.getTurnColor() == PieceColor.BLACK && value < bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
    
        if (bestMove == null && !legalMoves.isEmpty()) {
            bestMove = legalMoves.get(0); // fallback
        }
    
        callback.accept(bestMove);
    }
    

}
