package community.board.exception;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException() {
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }

    public MemberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
