/**
 * Created by Asmodean on 2016-09-25.
 */
public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParsingException(Throwable throwable) {
        super(throwable);
    }
}
