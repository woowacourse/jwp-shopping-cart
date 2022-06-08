package woowacourse.shoppingcart.domain.order;

import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetail {

    private final Long id;
    private final Product product;
    private final Quantity quantity;

    public OrderDetail(Product product, Quantity quantity) {
        this(null, product, quantity);
    }

    public OrderDetail(Long id, Product product, Quantity quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
