package assignment1.exceptions;

public class NotANumberException extends Exception {
    public NotANumberException(String input) {
        super("'" + input + "' is not a valid number.");
    }
}

