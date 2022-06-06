package woowacourse.shoppingcart.util;

public class StringUtil {

    private StringUtil() {}

    public static void validateLength(String target, int min, int max) {
        if (min != 0 && target.isBlank()) {
            throw new IllegalArgumentException("공백은 허용되지 않습니다.");
        }
        if (target.length() < min || target.length() > max) {
            throw new IllegalArgumentException("길이는 " + min + "이상, " + max + "이하여야 합니다.");
        }
    }
}
