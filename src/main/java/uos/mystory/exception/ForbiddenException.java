package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class ForbiddenException extends RuntimeException {

    private static final String defaultMessage = MessageManager.getMessage("error.forbidden.access");

    public ForbiddenException() {
        super(defaultMessage);
    }

    public ForbiddenException(String msg) {
        super(msg);
    }
}
