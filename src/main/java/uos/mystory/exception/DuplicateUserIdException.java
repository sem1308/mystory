package uos.mystory.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super();
    }

    public DuplicateUserIdException(String message) {
        super(message);
    }

    public DuplicateUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
