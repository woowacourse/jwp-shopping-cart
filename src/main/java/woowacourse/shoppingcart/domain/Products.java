package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
