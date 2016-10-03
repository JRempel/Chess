/**
 * Created by Asmodean on 2016-09-04.
 *
 * Move class for validating potential chess moves.
 *
 * File: Column Rank: Row
 */
public class Move {
    private int currentRank;
    private int currentFile;
    private int destinationRank;
    private int destinationFile;
    private Piece.Type pieceType;
    private Piece.Type castleWithPieceType;
    private boolean capture;

    public Move(Piece piece, int destinationRank, int destinationFile, boolean capture) {
        currentRank = piece.getRank();
        currentFile = piece.getFile();
        pieceType = piece.getType();
        this.destinationRank = destinationRank;
        this.destinationFile = destinationFile;
        this.capture = capture;
        castleWithPieceType = null;
    }

    public Move(Piece piece, int destinationRank, int destinationFile, boolean capture, Piece castleWith) {
        currentRank = piece.getRank();
        currentFile = piece.getFile();
        pieceType = piece.getType();
        this.destinationRank = destinationRank;
        this.destinationFile = destinationFile;
        this.capture = capture;
        this.castleWithPieceType = castleWith.getType();
    }

    public boolean isValid() {
        boolean valid = false;
        if (moveInGrid()) {
            switch (pieceType) {
                case PAWN:
                    if (validPawnRank()) {
                        if (capture) {
                            if (validPawnCaptureFile())
                                valid = true;
                        } else {
                            if (validPawnFile())
                                valid = true;
                        }
                    }
                case KNIGHT:
                    if (validKnightMove()) {
                        valid = true;
                    }
                case BISHOP:
                    if (validBishopMove()) {
                        valid = true;
                    }
                case ROOK:
                    if (validRookMove()) {
                        valid = true;
                    }
                case QUEEN:
                    if (validQueenMove()) {
                        valid = true;
                    }
                case KING:
                    if (validKingMove()) {
                        valid = true;
                    }
            }
        }
        return valid;
    }

    private boolean validPawnFile() {
        return destinationFile == currentFile;
    }

    private boolean validPawnCaptureFile() {
        return (Math.abs(destinationFile - currentFile) == 1);
    }

    private boolean validPawnRank() {
        if (pieceType.firstMove && (destinationRank - currentRank <= 2)) {
            return true;
        }
        return destinationRank - currentRank <= 1;
    }

    private boolean validKnightMove() {
        int rank = Math.abs(currentFile - destinationFile);
        int file = Math.abs(currentRank - destinationRank);
        return (rank == 1 && file == 2) || (rank == 2 && file == 1);
    }

    private boolean validBishopMove() {
        return Math.abs(currentRank - destinationRank) == (currentFile - destinationFile);
    }

    private boolean validRookMove() {
        return (Math.abs(currentRank - destinationRank) > 0 && (currentFile == destinationFile))
                || (Math.abs(currentFile - destinationFile) > 0) && (currentRank == destinationRank);
    }

    private boolean validQueenMove() {
        return (validRookMove() || validBishopMove());
    }

    private boolean validKingMove() {
        return (Math.abs(currentRank - destinationRank) <= 1) && (Math.abs(currentFile - destinationFile) <= 1);
    }

    private boolean moveInGrid() {
        return (destinationFile >= 1 && destinationFile <= 8)
                && destinationRank >= 1 && destinationRank <= 8;
    }
}
