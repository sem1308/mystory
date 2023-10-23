package uos.mystory.exception.massage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

    public static String getMessage(String key) {
        // 주어진 키에 해당하는 오류 메시지를 반환합니다.
        return messages.getString(key);
    }

    public static String getMessage(String key, Class c) {
        // 주어진 키와 클래스에 해당하는 오류 메시지를 반환합니다.
        return "["+c+"] "+messages.getString(key);
    }
}
