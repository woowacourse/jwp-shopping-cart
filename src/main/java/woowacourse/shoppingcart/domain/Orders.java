package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {
    private final Long id;
    private final List<OrderedProduct> orderedProducts;

    public Orders(final Long id, final List<OrderedProduct> orderedProducts) {
        this.id = id;
        this.orderedProducts = orderedProducts;
    }

    public Long getId() {
        return id;
    }

    public List<OrderedProduct> orderedProducts() {
        return orderedProducts;
    }
}
