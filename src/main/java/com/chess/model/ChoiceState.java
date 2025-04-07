package com.chess.model;

public class ChoiceState {
    private boolean isPieceChosen;
    private Square chosenSquare;

    public ChoiceState(){
        isPieceChosen= false;
        chosenSquare = null;
    }

    public boolean isPieceChosen(){
        return isPieceChosen;
    }
    public Square getChosenSquare() {
        return chosenSquare;
    }

    public void setChoice(Square chosenSquare){
        if (chosenSquare == null) {
            this.chosenSquare = null;
            this.isPieceChosen = false;
        }
        else {
            this.chosenSquare = chosenSquare;
            isPieceChosen = true;
        }
    }
    

}
