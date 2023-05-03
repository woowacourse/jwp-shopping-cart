package cart.vo.util;

import java.util.regex.Pattern;

public class VoUtil {

    private VoUtil() {
    }

    public static boolean isInvalidLength(String value, int lowerBoundExclusive, int upperBoundExclusive) {
        return value.length() < lowerBoundExclusive || upperBoundExclusive < value.length();
    }

    public static boolean isInvalidForm(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        return !p.matcher(value).matches();
    }

}
