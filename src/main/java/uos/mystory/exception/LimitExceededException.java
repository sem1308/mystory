package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class LimitExceededException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.exceeded");
    public LimitExceededException(String message) {
        super(message);
    }

    public LimitExceededException() {
        super(defaultMessage);
    }

    public LimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitExceededException(Throwable cause) {
        super(cause);
    }
}
