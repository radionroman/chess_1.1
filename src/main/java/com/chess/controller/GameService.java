package com.chess.controller;

import com.chess.player.Player;

public class GameService {
    private final Player whitePlayer;
    private final Player blackPlayer;

    public GameService(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }


    // public void nextTurn() {
    //     Player current = model.getTurnColor() == PieceColor.WHITE ? whitePlayer : blackPlayer;
    //     if (current instanceof HumanPlayer)view.setOnSquareClickListener((row, col) -> {
    //         onSquareClicked(row, col);
    //     }); 
    //     else view.setOnSquareClickListener((row, col) -> {System.out.println("disabled");});
    //     if (current != null) current.requestMove(model, (move) -> {
    //         model.executeMove(move);
    //         view.refresh(model.getBoardSnapshot());
    //         if (MoveValidator.getLegalMovesForColor(model.getGameState()).isEmpty()) {
    //             view.mate(model.getTurnColor().toString());
    //             return;
    //         }
    //         nextTurn();
    //     });
    // }
}
