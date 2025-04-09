package com.chess.player;

import java.awt.SystemColor;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.chess.model.*;
import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;

public class BotPlayer implements Player{
    Random random = new Random();

    public BotPlayer(){
        
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {

        GameState gameState = model.getGameState();
        Piece[][] board = gameState.getBoard();
        PieceColor turnColor = gameState.getTurnColor();
        List<Square> moves;
        
        Square from = null;
        Square to = null;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) continue;
                if (board[i][j].getPieceColor() == turnColor) {
                    moves = board[i][j].getLegalMoves(board, i, j);
                    if (!moves.isEmpty()){
                        from = new Square(i, j);
                        to = moves.get(random.nextInt(moves.size()));
                    }
                }
            }
        }
        if (from == null && to == null) {
            System.out.println("NOMOVESFORBOTTT");
        }

        Move move = new Move(from, to, gameState);
        System.out.println(move);
        callback.accept(move);
        
    }

}
