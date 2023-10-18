package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class ResourceNotFoundException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.user.notfound");
    public ResourceNotFoundException() {
        super(defaultMessage);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
