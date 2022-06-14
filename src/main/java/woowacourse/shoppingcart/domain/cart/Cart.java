package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.domain.product.Product;

public class Cart {

    private final Long id;
    private final Long customerId;
    private final Product product;

    public Cart(Long id, Long customerId, Product product) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }
}
