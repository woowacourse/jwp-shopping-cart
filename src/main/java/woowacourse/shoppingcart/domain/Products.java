package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;
import woowacourse.shoppingcart.exception.InvalidPageException;

public class Products {

    private final List<Product> value;

    public Products(List<Product> value) {
        this.value = value;
    }

    public List<Product> getValue() {
        return Collections.unmodifiableList(value);
    }

    public Products getProductsOfPage(int size, int page) {
        int countOfPage = value.size() / size + 1;
        int sizeOfLastPage = value.size() % size;
        if (page < countOfPage) {
            return new Products(value.subList(size * (page - 1), size * page));
        }
        if (page == countOfPage) {
            return new Products(value.subList(value.size() - sizeOfLastPage, value.size()));
        }
        throw new InvalidPageException();
    }
}
