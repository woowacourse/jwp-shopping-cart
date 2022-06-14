package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.exception.InvalidPageException;

public class Page {

    private static final int START_INDEX = 1;

    private final int number;
    private final int size;

    private Page(final int number, final int size) {
        this.number = number;
        this.size = size;
    }

    public static Page of(final Integer number, final Integer size) {
        validateNumber(number);
        validateSize(size);
        return new Page(calculatePageNumber(number, size), size);
    }

    private static void validateNumber(final Integer value) {
        if (value == null || value < 0) {
            throw new InvalidPageException();
        }
    }

    private static void validateSize(final Integer size) {
        if (size == null || size < 1) {
            throw new InvalidPageException();
        }
    }

    private static int calculatePageNumber(final Integer number, final Integer size) {
        return (number - START_INDEX) * size;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }
}
