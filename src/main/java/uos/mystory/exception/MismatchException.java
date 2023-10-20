package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class MismatchException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.mismatch");
    public MismatchException(String message) {
        super(message);
    }

    public MismatchException() {
        super(defaultMessage);
    }

    public MismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchException(Throwable cause) {
        super(cause);
    }
}
