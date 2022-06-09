package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    private final List<Product> value;

    public Products(List<Product> value) {
        this.value = value;
    }

    public Products getProductsOfPage(Integer size, Integer page) {
        int countOfPage = value.size() / size + 1;
        int sizeOfLastPage = value.size() % size;

        if (page < countOfPage) {
            return new Products(value.subList(size * (page - 1), size * page));
        }
        if (page == countOfPage) {
            return new Products(value.subList(value.size() - sizeOfLastPage, value.size()));
        }
        return new Products(new ArrayList<>());
    }

    public List<Product> getValue() {
        return Collections.unmodifiableList(value);
    }
}
