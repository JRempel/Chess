/**
 * Created by Asmodean on 2016-09-04.
 *
 * Board class contains all location metadata for
 * an 8x8 chess-board & game-state.
 */
public class Board {
    public static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7;
    public static final int n1 = 7, n2 = 6, n3 = 5, n4 = 4, n5 = 3, n6 = 2, n7 = 1, n8 = 0;

    public static enum File {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);

        private int value;
        File(int file) {
            this.value = file;
        }

        public int getValue() {
            return this.value;
        }
    }

    public Piece[][] grid;
    // number of half-moves since last pawn advance or capture
    private int halfMoves = 0;
    private int fullMoves = 1;
    private boolean whiteQueenSideCastle = true;
    private boolean whiteKingSideCastle = true;
    private boolean blackQueenSideCastle = true;
    private boolean blackKingSideCastle = true;
    private boolean whiteTurn = true;
    private String enPassantTarget = "-";

    public Board(){
        grid = new Piece[8][8];
    }

    public int getHalfMoves() {
        return halfMoves;
    }

    public int getFullMoves() {
        return fullMoves;
    }

    public void toggleTurn() {
        whiteTurn = !whiteTurn;
        if (whiteTurn){
            fullMoves++;
        }
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setEnPassantTarget(String target) {
        enPassantTarget = target;
    }

    public String getEnPassantTarget() {
        return enPassantTarget;
    }

    public void setFullMoves(int moves) {
        fullMoves = moves;
    }

    public void setHalfMoves(int moves) {
        halfMoves = moves;
    }

    public void setWhiteQueenSideCastle(boolean whiteQueenSideCastle) {
        this.whiteQueenSideCastle = whiteQueenSideCastle;
    }

    public boolean whiteQueenSideCanCastle() {
        return whiteQueenSideCastle;
    }

    public void setWhiteKingSideCastle(boolean whiteKingSideCastle) {
        this.whiteKingSideCastle = whiteKingSideCastle;
    }

    public boolean whiteKingSideCanCastle() {
        return whiteKingSideCastle;
    }

    public void setBlackQueenSideCastle(boolean blackQueenSideCastle) {
        this.blackQueenSideCastle = blackQueenSideCastle;
    }

    public boolean blackQueenSideCanCastle() {
        return blackQueenSideCastle;
    }

    public void setBlackKingSideCastle(boolean blackKingSideCastle) {
        this.blackKingSideCastle = blackKingSideCastle;
    }

    public boolean blackKingSideCanCastle() {
        return blackKingSideCastle;
    }

    public void initialize() {
        grid[n8][A] = new Piece(Piece.Type.ROOK, Piece.Colour.BLACK);
        grid[n8][H] = new Piece(Piece.Type.ROOK, Piece.Colour.BLACK);
        grid[n8][B] = new Piece(Piece.Type.KNIGHT, Piece.Colour.BLACK);
        grid[n8][G] = new Piece(Piece.Type.KNIGHT, Piece.Colour.BLACK);
        grid[n8][C] = new Piece(Piece.Type.BISHOP, Piece.Colour.BLACK);
        grid[n8][F] = new Piece(Piece.Type.BISHOP, Piece.Colour.BLACK);
        grid[n8][D] = new Piece(Piece.Type.QUEEN, Piece.Colour.BLACK);
        grid[n8][E] = new Piece(Piece.Type.KING, Piece.Colour.BLACK);
        for (int i = 0; i < grid.length; i++) {
            grid[n7][i] = new Piece(Piece.Type.PAWN, Piece.Colour.BLACK);
        }

        grid[n1][A] = new Piece(Piece.Type.ROOK, Piece.Colour.WHITE);
        grid[n1][H] = new Piece(Piece.Type.ROOK, Piece.Colour.WHITE);
        grid[n1][B] = new Piece(Piece.Type.KNIGHT, Piece.Colour.WHITE);
        grid[n1][G] = new Piece(Piece.Type.KNIGHT, Piece.Colour.WHITE);
        grid[n1][C] = new Piece(Piece.Type.BISHOP, Piece.Colour.WHITE);
        grid[n1][F] = new Piece(Piece.Type.BISHOP, Piece.Colour.WHITE);
        grid[n1][D] = new Piece(Piece.Type.QUEEN, Piece.Colour.WHITE);
        grid[n1][E] = new Piece(Piece.Type.KING, Piece.Colour.WHITE);
        for (int i = 0; i < grid.length; i++) {
            grid[n2][i] = new Piece(Piece.Type.PAWN, Piece.Colour.WHITE);
        }
    }

    public void fillRank(int rank, Piece[] pieces) {
        if (pieces.length != 8 || rank < 1 || rank > 8) {
            // add logger
            return;
        }
        for (int i = 0; i < grid.length; i++) {
            grid[grid.length - rank][i] = pieces[i];
        }
    }

    public int size() {
        return grid.length;
    }

    public void display() {
        System.out.print("\n   ----+---+---+---+---+---+---+----\n");
        for (int row = 0; row < grid.length; row++) {
            System.out.print(grid.length - row + "  ");
            System.out.print("|");
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == null) {
                    if ((row + column) % 2 != 0) {
                        System.out.print(" X ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    switch (grid[row][column].getColour()) {
                        case BLACK:
                            System.out.print("*" + grid[row][column].getCharacter() + "*");
                            break;
                        case WHITE:
                            System.out.print(" " + grid[row][column].getCharacter() + " ");
                            break;
                    }
                }
                System.out.print("|");
            }
            System.out.print("\n   ----+---+---+---+---+---+---+----\n");
        }
        System.out.println("     A   B   C   D   E   F   G   H\n");
    }
}
