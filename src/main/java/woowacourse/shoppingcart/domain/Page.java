package woowacourse.shoppingcart.domain;

import woowacourse.exception.badRequest.InvalidPageException;

public class Page {

    private final int begin;
    private final int size;

    private Page(int begin, int size) {
        this.begin = begin;
        this.size = size;
    }

    public static Page of(final Integer begin, final Integer size) {
        validatePage(begin, size);
        return new Page((begin - 1) * size, size);
    }

    private static void validatePage(final Integer begin, final Integer size) {
        if (begin == null || begin < 0) {
            throw new InvalidPageException();

        }

        if (size == null || size < 1) {
            throw new InvalidPageException();
        }
    }

    public int getBegin() {
        return begin;
    }

    public int getSize() {
        return size;
    }
}
