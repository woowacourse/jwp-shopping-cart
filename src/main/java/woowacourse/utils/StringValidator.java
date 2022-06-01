package woowacourse.utils;

import woowacourse.shoppingcart.exception.ValidationException;

public class StringValidator {

    public static void validateLength(final int min, final int max, final String target,
                                         final ValidationException e) {
        if (target.length() < min || target.length() > max) {
            throw e;
        }
    }

    public static void validateNullOrBlank(final String target, final ValidationException e) {
        if (target == null || target.isBlank()) {
            throw e;
        }
    }
}
