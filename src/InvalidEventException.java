public class InvalidEventException extends Exception {
    private final int lineNumber;

    public InvalidEventException(int lineNumber, String message) {
        super("Line " + lineNumber + ": " + message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
