package community.board.exception;

public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException() {
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }
}
