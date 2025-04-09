package com.chess.model;

public class SelectionState {
    private boolean isPieceSelected;
    private Square selectedSquare;

    public SelectionState(){
        isPieceSelected= false;
        selectedSquare = null;
    }

    public boolean isPieceSelected(){
        return isPieceSelected;
    }
    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelection(Square chosenSquare){
        if (chosenSquare == null) {
            this.selectedSquare = null;
            this.isPieceSelected = false;
        }
        else {
            this.selectedSquare = chosenSquare;
            this.isPieceSelected = true;
        }
    }
    

}
