import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Asmodean on 2016-09-25.
 *
 * Parser class for converting FEN or PGN representation to
 * a board-state or vice versa.
 */
public class Parser {

    public static Board parseFEN(String FEN) throws ParsingException {
        ArrayList<String> values = new ArrayList<>(Arrays.asList(FEN.split("(/)|( )")));
        if (!isFENValid(values)) {
            throw new ParsingException("Invalid FEN string.");
        }

        Board board = new Board();

        for (int i = 0; i < 8; i++) {
            char[] toParse = values.get(i).toCharArray();
            Piece[] tofill = new Piece[8];

            int currentIndex = 0;
            int num = 1;
            for (char c: toParse) {
                if (c >= '0' && c <= '9') {
                    num = c - 48;
                    while (num > 0) {
                        tofill[currentIndex] = null;
                        num--;
                        currentIndex++;
                    }
                } else {
                    switch (c) {
                        case 'p':
                            tofill[currentIndex] = new Piece(Piece.Type.PAWN, Piece.Colour.BLACK);
                            break;
                        case 'P':
                            tofill[currentIndex] = new Piece(Piece.Type.PAWN, Piece.Colour.WHITE);
                            break;
                        case 'r':
                            tofill[currentIndex] = new Piece(Piece.Type.ROOK, Piece.Colour.BLACK);
                            break;
                        case 'R':
                            tofill[currentIndex] = new Piece(Piece.Type.ROOK, Piece.Colour.WHITE);
                            break;
                        case 'n':
                            tofill[currentIndex] = new Piece(Piece.Type.KNIGHT, Piece.Colour.BLACK);
                            break;
                        case 'N':
                            tofill[currentIndex] = new Piece(Piece.Type.KNIGHT, Piece.Colour.WHITE);
                            break;
                        case 'b':
                            tofill[currentIndex] = new Piece(Piece.Type.BISHOP, Piece.Colour.BLACK);
                            break;
                        case 'B':
                            tofill[currentIndex] = new Piece(Piece.Type.BISHOP, Piece.Colour.WHITE);
                            break;
                        case 'q':
                            tofill[currentIndex] = new Piece(Piece.Type.QUEEN, Piece.Colour.BLACK);
                            break;
                        case 'Q':
                            tofill[currentIndex] = new Piece(Piece.Type.QUEEN, Piece.Colour.WHITE);
                            break;
                        case 'k':
                            tofill[currentIndex] = new Piece(Piece.Type.KING, Piece.Colour.BLACK);
                            break;
                        case 'K':
                            tofill[currentIndex] = new Piece(Piece.Type.KING, Piece.Colour.WHITE);
                            break;
                    }
                    currentIndex++;
                }
            }

            board.fillRank(board.size() - i, tofill);
        }

        if (values.get(8).startsWith("b")) {
            if (board.isWhiteTurn()) {
                board.toggleTurn();
            }
        } else {
            if (!board.isWhiteTurn()) {
                board.toggleTurn();
            }
        }

        board.setBlackKingSideCastle(false);
        board.setBlackQueenSideCastle(false);
        board.setWhiteKingSideCastle(false);
        board.setWhiteQueenSideCastle(false);

        if (values.get(9).contains("K")) {
            board.setWhiteKingSideCastle(true);
        }
        if (values.get(9).contains("Q")) {
            board.setWhiteQueenSideCastle(true);
        }
        if (values.get(9).contains("k")) {
            board.setBlackKingSideCastle(true);
        }
        if (values.get(9).contains("q")) {
            board.setBlackQueenSideCastle(true);
        }


        board.setEnPassantTarget(values.get(10));
        board.setHalfMoves(Integer.parseInt(values.get(11)));
        board.setFullMoves(Integer.parseInt(values.get(12)));

        System.out.println("Filling board with the following FEN notation: " + FEN);

        return board;
    }

    private static boolean isFENValid(ArrayList<String> FEN) {
        if (FEN.size() != 13) {
            return false;
        }

        // http://chess.stackexchange.com/questions/1482/how-to-know-when-a-fen-position-is-legal
        return true;
    }

    public static String printFEN(Board board) {
        String FENrepresentation = "";
        int emptySpaces = 0;

        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                if (board.grid[column][row] != null) {
                    if (emptySpaces != 0) {
                        FENrepresentation += emptySpaces;
                        emptySpaces = 0;
                    }
                    switch (board.grid[column][row].getColour()) {
                        case WHITE:
                            FENrepresentation += board.grid[column][row].getCharacter();
                            break;
                        case BLACK:
                            FENrepresentation += Character.toLowerCase(board.grid[column][row].getCharacter());
                            break;
                    }
                } else {
                    emptySpaces++;
                }
            }
            if (emptySpaces != 0) {
                FENrepresentation += emptySpaces;
                emptySpaces = 0;
            }
            if (column != 7) {
                FENrepresentation += "/";
            }
        }

        FENrepresentation += " " + (board.isWhiteTurn()?"w":"b") + " ";

        if (!board.whiteKingSideCanCastle() && !board.whiteQueenSideCanCastle() && !board.blackKingSideCanCastle() && !board.blackQueenSideCanCastle()) {
            FENrepresentation += "-";
        } else {
            if (board.whiteKingSideCanCastle()) {
                FENrepresentation += "K";
            }
            if (board.whiteQueenSideCanCastle()) {
                FENrepresentation += "Q";
            }
            if (board.blackKingSideCanCastle()) {
                FENrepresentation += "k";
            }
            if (board.blackQueenSideCanCastle()) {
                FENrepresentation += "q";
            }
        }

        FENrepresentation += " " + board.getEnPassantTarget() + " " + board.getHalfMoves() + " " + board.getFullMoves();
        return FENrepresentation;
    }

    public static Board parsePGN(String PGN) {
        Board board = new Board();

        String clean = PGN.replaceAll("\n", "").replaceAll("[0-9]*\\. ", "").replaceAll("\\{.*\\}", "");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(clean.split("(?<!\\G\\S+)\\s")));

        for (String s: values) {
            System.out.println(s);
        }

        if (isPGNValid(values)) {

        }

        return board;
    }

    private static boolean isPGNValid(ArrayList<String> PGN) {

        return true;
    }

    public static String printPGN(Board board) {
        String PGNrepresentation = "";



        return PGNrepresentation;
    }

    public static Board parseMove(String move) {
        Board board = new Board();


        return board;
    }
}