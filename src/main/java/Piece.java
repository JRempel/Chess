/**
 * Created by Asmodean on 2016-09-04.
 *
 * Class containing all metadata for chess pieces.
 */
public class Piece {
    public enum Type {
        KING('K', 10), QUEEN('Q', 9), ROOK('R', 5), BISHOP('B', 3), KNIGHT('N', 3), PAWN('P', 1);

        public char character;
        public int value;
        public boolean firstMove = false;

        Type(char c, int v) {
            character = c;
            value = v;
            if (c == 'P')
                firstMove = true;
        }
    }

    public enum Colour {BLACK, WHITE}

    private Type type;
    private Colour colour;
    private int rank;
    private int file;

    public Piece(Type type, Colour colour) {
        this.type = type;
        this.colour = colour;
    }

    public boolean isWhite() {
        return this.colour.equals(Colour.WHITE);
    }

    public boolean isBlack() {
        return this.colour.equals(Colour.BLACK);
    }

    public int getValue() {
        return this.type.value;
    }

    public Type getType() {
        return this.type;
    }

    public char getCharacter() {
        return this.type.character;
    }

    public Colour getColour() {
        return this.colour;
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }

    public String toString() {
        return (this.colour.equals(Colour.WHITE) ? type.toString() : type.toString().toLowerCase());
    }
}
