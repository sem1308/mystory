package uos.mystory.utils;

import uos.mystory.exception.massage.MessageManager;

import java.util.Arrays;

public class Validator {

    public static <T> void validateNull(T object) {
        if(object==null) throw new NullPointerException(MessageManager.getMessage("error.null"));
    }

    @SafeVarargs
    public static <T> void validateNull(T ...objects) {
        Arrays.stream(objects).forEach(Validator::validateNull);
    }
}
