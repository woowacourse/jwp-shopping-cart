package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> slice(int size, int page) {
        var fromIndex = size * (page - 1);
        var toIndex = size * page;
        var totalSize = products.size();

        if (toIndex > totalSize) {
            return new ArrayList<>(products.subList(fromIndex, totalSize));
        }
        return new ArrayList<>(products.subList(fromIndex, toIndex));
    }
}
