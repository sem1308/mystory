package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class DuplicateException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.duplicate");

    public DuplicateException() {
        super(defaultMessage);
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }
}
