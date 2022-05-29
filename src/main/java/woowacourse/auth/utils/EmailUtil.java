package woowacourse.auth.utils;

public class EmailUtil {

    private static final int IDENTIFIER_INDEX = 0;

    public static String getIdentifier(String email) {
        return email.split("@")[IDENTIFIER_INDEX];
    }
}
