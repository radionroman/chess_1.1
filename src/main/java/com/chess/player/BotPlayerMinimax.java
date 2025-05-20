package com.chess.player;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.SwingWorker;

import com.chess.model.Board;
import com.chess.model.ChessModel;
import com.chess.model.GameState;
import com.chess.model.PieceColor;
import com.chess.model.moves.Move;
import com.chess.model.moves.MoveValidator;
import static com.chess.utils.Constants.BOARD_COLS;
import static com.chess.utils.Constants.BOARD_ROWS;

public class BotPlayerMinimax extends Player {

    int[][] PAWN_TABLE = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    int[][] KNIGHT_TABLE = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    };
    int[][] BISHOP_TABLE = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };
    int[][] ROOK_TABLE = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 0, 0, 0, 5, 5, 0, 0, 0 }
    };
    int[][] QUEEN_TABLE = {
            { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    };
    int[][] KING_MID_TABLE = {
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { 20, 30, 10, 0, 0, 10, 30, 20 }
    };
    int[][] KING_END_TABLE = {
            { -50, -40, -30, -20, -20, -30, -40, -50 },
            { -30, -20, -10, 0, 0, -10, -20, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -50, -30, -30, -30, -30, -30, -30, -50 }
    };

    private final int depthF;
    private Consumer<Integer> progressBar;
    private final PieceColor myColor;

    public BotPlayerMinimax(int depth, PieceColor myColor) {
        this.depthF = depth;
        this.myColor = myColor;
    }

    public void connectProgressBar(Consumer<Integer> l){
        progressBar = l;
    }

    private int evaluate(GameState gameState) {

        int value = 0;
        Board board = gameState.getBoard();
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (board.isEmptyAt(i, j))
                    continue;
                switch (board.getPieceAt(i, j).getPieceType()) {
                    case KING -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + KING_MID_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + KING_MID_TABLE[7 - i][j];
                    }
                    case QUEEN -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + QUEEN_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + QUEEN_TABLE[7 - i][j];
                    }
                    case ROOK -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + ROOK_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + ROOK_TABLE[7 - i][j];
                    }
                    case BISHOP -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + BISHOP_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + BISHOP_TABLE[7 - i][j];
                    }
                    case KNIGHT -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + KNIGHT_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + KNIGHT_TABLE[7 - i][j];
                    }
                    case PAWN -> {
                        if (board.getPieceAt(i, j).getPieceColor() == PieceColor.WHITE)
                            value += board.getPieceAt(i, j).getRank() + PAWN_TABLE[i][j];
                        else
                            value -= board.getPieceAt(i, j).getRank() + PAWN_TABLE[7 - i][j];
                    }
                }

            }
        }
        return value;
    }

    private int minimax(GameState child, int depth, boolean maximizingPlayer) {
        List<Move> legalMoves = MoveValidator.getLegalMovesForColor(child);
        if (depth == 0 || legalMoves.isEmpty()) {
            return evaluate(child);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                // System.out.println(move);

                child.executeMove(move);

                int eval = minimax(child, depth - 1, false); // switch to minimizing
                maxEval = Math.max(maxEval, eval);
                child.undoMove();
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                child.executeMove(move);
                int eval = minimax(child, depth - 1, true); // switch to maximizing
                minEval = Math.min(minEval, eval);
                child.undoMove();
            }
            return minEval;
        }
    }

    @Override
    public void requestMove(ChessModel model, Consumer<Move> callback) {
        
        SwingWorker<Void, Integer> worker = new SwingWorker<Void,Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                GameState gameState = model.getGameState().copy();
                List<Move> legalMoves = MoveValidator.getLegalMovesForColor(gameState);
                PieceColor turnColor = gameState.getTurnColor();
                if (turnColor != myColor) {
                    System.out.println("Wrong color");
                    throw new RuntimeException("The turn color is wrong!");
                }
                Move bestMove = null;
                int bestValue = (turnColor == PieceColor.WHITE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                GameState child = gameState;
                int totalMoves = legalMoves.size();
                int moveNumber = 0;
                publish(0);
                for (Move move : legalMoves) {
                    child.executeMove(move);
                    int value = minimax(child, depthF, turnColor == PieceColor.BLACK); // depth 3 is good for
                                                                                                      // start
        
                    if (turnColor == PieceColor.WHITE && value > bestValue) {
                        bestValue = value;
                        bestMove = move;
                    } else if (turnColor == PieceColor.BLACK && value < bestValue) {
                        bestValue = value;
                        bestMove = move;
                    }
                    child.undoMove();
                    moveNumber++;
                    publish(Math.floorDiv(moveNumber*100, totalMoves));
                }
        
                if (bestMove == null && !legalMoves.isEmpty()) {
                    bestMove = legalMoves.get(0); // fallback
                }
        
                callback.accept(bestMove);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                progressBar.accept(chunks.get(chunks.size()-1));
                
            }
            
            
        };
        worker.execute();
        
        
    }

}
