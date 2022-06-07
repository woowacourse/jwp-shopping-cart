package woowacourse.shoppingcart.domain;

import java.util.List;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;

public class Products {
    private final List<Product> value;

    public Products(List<Product> value) {
        this.value = value;
    }

    public List<Product> calculatePage(int pageNumber, int limit) {
        validateLimit(limit);
        int startIndex = getStartIndex(pageNumber, limit);
        validateStartIndex(startIndex);
        return value.subList(startIndex, getLastIndex(limit, startIndex));
    }

    private void validateLimit(int limit) {
        if (limit < 1) {
            throw new InvalidInputException("페이지");
        }
    }

    private int getStartIndex(int pageNumber, int limit) {
        return limit * (pageNumber - 1);
    }

    private void validateStartIndex(int startIndex) {
        if (startIndex >= value.size()) {
            throw new InvalidInputException("페이지");
        }
    }

    private int getLastIndex(int limit, int startIndex) {
        int lastIndex = startIndex + limit;
        if (lastIndex >= value.size()) {
            lastIndex = value.size();
        }
        return lastIndex;
    }
}
