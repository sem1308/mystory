package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class DuplicateUserIdException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.user.user_id.duplicate");

    public DuplicateUserIdException() {
        super(defaultMessage);
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
