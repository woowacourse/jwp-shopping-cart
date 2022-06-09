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
        int countOfPage = getProductsCount() / size + 1;
        int sizeOfLastPage = getProductsCount() % size;

        if (page < countOfPage) {
            return new Products(value.subList(size * (page - 1), size * page));
        }
        if (page == countOfPage) {
            return new Products(value.subList(getProductsCount() - sizeOfLastPage, getProductsCount()));
        }
        return new Products(new ArrayList<>());
    }

    public int getProductsCount() {
        return value.size();
    }

    public List<Product> getValue() {
        return Collections.unmodifiableList(value);
    }
}
