package com.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.chess.model.pieces.Piece;
import com.chess.model.pieces.PieceFactory;
import com.chess.model.pieces.PieceType;

public class GameState {
    private final Piece[][] board;
    private PieceColor turnColor;
    private Move lastMove = null;

    public GameState() {
        board = boardInit();
        turnColor = PieceColor.WHITE;
    }

    public GameState(Piece[][] board, PieceColor turnColor, Move lastMove) {
        this.board = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.board[i][j] = board[i][j] == null ? null : board[i][j].copy();
            }
        }
        this.turnColor = turnColor;
        this.lastMove = lastMove;
    }

    public GameState copy() {
        GameState gameStateCopy = new GameState(board, turnColor, lastMove);
        return gameStateCopy;
    }

    private Piece[][] boardInit() {
        Piece[][] tempBoard = new Piece[8][8];
        PieceType[] order = new PieceType[] {
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.QUEEN,
                PieceType.KING,
                PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK,
        };
        for (int i = 0; i < tempBoard.length; i++) {
            tempBoard[0][i] = PieceFactory.createPiece(order[i], PieceColor.BLACK);
            tempBoard[1][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.BLACK);
            tempBoard[6][i] = PieceFactory.createPiece(PieceType.PAWN, PieceColor.WHITE);
            tempBoard[7][i] = PieceFactory.createPiece(order[i], PieceColor.WHITE);
        }
        return tempBoard;
    }

    public void movePiece(Move move) {
        lastMove = move;
        Square from = move.getFrom();
        Square to = move.getTo();
        Piece piece = board[from.getRow()][from.getCol()];
        MoveType type = move.getType();
        piece.setHasMoved();
        switch (type) {
            case DEFAULT -> {
                board[to.getRow()][to.getCol()] = piece;
                board[from.getRow()][from.getCol()] = null;

            }
            case PROMOTION_QUEEN -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.QUEEN, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_BISHOP -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.BISHOP, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_ROOK -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.ROOK, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case PROMOTION_KNIGHT -> {
                board[to.getRow()][to.getCol()] = PieceFactory.createPiece(PieceType.KNIGHT, turnColor);
                board[from.getRow()][from.getCol()] = null;
            }
            case CASTLING_LONG -> {
                board[from.getRow()][from.getCol()] = null;
                board[to.getRow()][to.getCol()] = piece;
                board[to.getRow()][to.getCol() - 1] = board[to.getRow()][to.getCol() + 1];
                board[to.getRow()][to.getCol() + 1] = null;
            }
            case CASTLING_SHORT -> {
                board[from.getRow()][from.getCol()] = null;
                board[to.getRow()][to.getCol()] = piece;
                board[to.getRow()][to.getCol() + 1] = board[to.getRow()][to.getCol() - 1];
                board[to.getRow()][to.getCol() - 1] = null;
            }
            case EN_PASSANT -> {
                // System.out.println("FROM: " + from);
                // System.out.println("TO: " + to);
                // System.out.println("Captured at: " + from.getRow() + ", " + to.getCol());
                // System.out.println("Board before capture: " + board[from.getRow()][to.getCol()]);
                board[from.getRow()][from.getCol()] = null;
                board[from.getRow()][to.getCol()] = null; // capture
                board[to.getRow()][to.getCol()] = piece;
                // System.out.println("Board after capture: " + board[from.getRow()][to.getCol()]);

            }
        }
        if (turnColor == PieceColor.WHITE)
            turnColor = PieceColor.BLACK;
        else
            turnColor = PieceColor.WHITE;

        // if(isCheckPresent(turnColor)){
        // System.out.println("The " + turnColor + " king is in check!");
        // }

    }

    // Game rules
    private boolean isCheckPresent(PieceColor color) {
        PieceOnSquare king = null;
        List<PieceOnSquare> myPieces = getPiecesOnSquare(color);
        for (PieceOnSquare pieceOnSquare : myPieces) {
            if (pieceOnSquare.getPiece().getPieceType() == PieceType.KING) {
                king = pieceOnSquare;
            }
        }
        List<PieceOnSquare> enemyPieces = getPiecesOnSquare(
                color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        if (king == null)
            return false;
        for (PieceOnSquare pieceOnSquare : enemyPieces) {
            if (pieceOnSquare.getLegalMovesTo(board).contains(king.getSquare()))
                return true;
        }
        return false;
    }
    private boolean isCheckPresent(PieceColor color, Square square) {

        List<PieceOnSquare> enemyPieces = getPiecesOnSquare(
                color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        for (PieceOnSquare pieceOnSquare : enemyPieces) {
            if (pieceOnSquare.getLegalMovesTo(board).contains(square))
                return true;
        }
        return false;
    }

    // Basic Getters
    public Piece[][] getBoard() {
        return board;
    }

    public PieceColor getTurnColor() {
        return turnColor;
    }

    // Advanced getters
    public List<PieceOnSquare> getPiecesOnSquare(PieceColor color) {
        ArrayList<PieceOnSquare> pieces = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null)
                    continue;
                if (board[i][j].getPieceColor() != color)
                    continue;
                pieces.add(new PieceOnSquare(board[i][j], new Square(i, j)));
            }
        }
        return pieces;
    }

    public List<Move> getPseudoLegalMovesForColor(PieceColor color) {
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> pieceMoves;
        List<PieceOnSquare> pieces = getPiecesOnSquare(color);
        for (PieceOnSquare pieceOnSquare : pieces) {
            pieceMoves = pieceOnSquare.getLegalMoves(board, this);

            if (pieceOnSquare.getPiece().getPieceType() == PieceType.PAWN) { // promotion and en passant checks
                moves.addAll(getEnPassantMoves(pieceOnSquare));

                if (!getPromotionMoves(pieceOnSquare).isEmpty()) {
                    moves.addAll(getPromotionMoves(pieceOnSquare));
                    continue;
                }
            }

            moves.addAll(pieceMoves);
            if (pieceOnSquare.getPiece().getPieceType() == PieceType.KING) { // castling checks
                moves.addAll(getCastlingMoves(pieceOnSquare, 0));
                moves.addAll(getCastlingMoves(pieceOnSquare, 7));
            }

        }

        return moves;
    }

    public List<Move> getLegalMovesForColor(PieceColor color) {
        List<Move> pseudoLegalMoves = getPseudoLegalMovesForColor(color);
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move move : pseudoLegalMoves) {
            GameState testState = this.copy();
            testState.movePiece(move);
            if (!testState.isCheckPresent(color))
                legalMoves.add(move);
        }
        return legalMoves;
    }

    private List<Move> getEnPassantMoves(PieceOnSquare pieceOnSquare) {
        ArrayList<Move> moves = new ArrayList<>();
        Square from = pieceOnSquare.getSquare();

        if (from.getRow() != (pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? 3 : 4))
            return moves;

        int direction = pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? -1 : 1;
        Square leftCaptured = new Square(from.getRow(), from.getCol() - 1);
        Square rightCaptured = new Square(from.getRow(), from.getCol() + 1);
        Square captured;
        // System.out.println(lastMove);
        if (lastMove.getTo().equals(leftCaptured))
            captured = leftCaptured;
        else if (lastMove.getTo().equals(rightCaptured))
            captured = rightCaptured;
        else
            return moves;
        // System.out.println("ENPAssANT CHECL");
        if (!lastMove.getFrom().equals(new Square(captured.getRow() + direction * 2, captured.getCol())))
            return moves;
        if (board[lastMove.getTo().getRow()][lastMove.getTo().getCol()].getPieceType() != PieceType.PAWN)
            return moves;
        moves.add(new Move(from, new Square(captured.getRow() + direction, captured.getCol()), this.copy(),
                MoveType.EN_PASSANT));
        return moves;
    }

    private List<Move> getCastlingMoves(PieceOnSquare pieceOnSquare, int rookCol) {
        ArrayList<Move> moves = new ArrayList<>();
        
        Piece king = pieceOnSquare.getPiece();
        if (king.hasMoved()) return moves;
        Square kingSquare = pieceOnSquare.getSquare();
        int kingCol = kingSquare.getCol();
        Square rookSquare = new Square(kingSquare.getRow(), rookCol);
        Piece rook = board[rookSquare.getRow()][rookSquare.getCol()];
        if (rook == null || rook.hasMoved())return moves;
        int start, end;
        
        if (kingCol > rookCol) {
            start = rookCol+1;
            end = kingCol;
        } else {
            start = kingCol;
            end = rookCol-1;
        }
        // System.out.println(start + " " + end);
        for (int i = start; i <= end; i++){
            if (isCheckPresent(turnColor, new Square(kingSquare.getRow(), i)) || (i != kingCol && board[kingSquare.getRow()][i] != null)) {
                return moves;
            }
        }
        if (kingCol < rookCol)moves.add(new Move(kingSquare, new Square(kingSquare.getRow(), end), this.copy(), MoveType.CASTLING_LONG));
        else moves.add(new Move(kingSquare, new Square(kingSquare.getRow(), end), this.copy(), MoveType.CASTLING_SHORT));
        return moves;

    }

    private List<Move> getPromotionMoves(PieceOnSquare pieceOnSquare) {
        ArrayList<Move> moves = new ArrayList<>();
        Square from = pieceOnSquare.getSquare();
        if (from.getRow() != (pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? 1 : 6))
            return moves;
        int direction = pieceOnSquare.getPiece().getPieceColor() == PieceColor.WHITE ? -1 : 1;
        Square to = new Square(from.getRow() + direction, from.getCol());
        moves.add(new Move(from, to, this.copy(), MoveType.PROMOTION_BISHOP));
        moves.add(new Move(from, to, this.copy(), MoveType.PROMOTION_ROOK));
        moves.add(new Move(from, to, this.copy(), MoveType.PROMOTION_KNIGHT));
        moves.add(new Move(from, to, this.copy(), MoveType.PROMOTION_QUEEN));
        return moves;

    }

}
