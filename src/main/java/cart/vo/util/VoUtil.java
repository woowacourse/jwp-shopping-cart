package cart.vo.util;

public class VoUtil {

    private VoUtil() {
    }

    public static boolean isInvalidLength(String value, int lowerBoundExclusive, int upperBoundExclusive) {
        return value.length() < lowerBoundExclusive || upperBoundExclusive < value.length();
    }

}
