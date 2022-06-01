package woowacourse.utils;

import woowacourse.shoppingcart.exception.ValidationException;

public class StringValidator {

    public static boolean validateLength(final int min, final int max, final String target,
                                         final ValidationException e) {
        return !(target.length() < min || target.length() > max);
    }

    public static boolean validateNullOrBlank(final String target, final ValidationException e) {
        return !(target.isEmpty() || target.isBlank());
    }

}
