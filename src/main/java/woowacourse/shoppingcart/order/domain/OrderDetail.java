package woowacourse.shoppingcart.order.domain;

import woowacourse.shoppingcart.cart.domain.Quantity;
import woowacourse.shoppingcart.product.domain.Product;

public class OrderDetail {

    private Product product;
    private Quantity quantity;

    private OrderDetail() {
    }

    public OrderDetail(final Product product, final int quantity) {
        this.product = product;
        this.quantity = Quantity.from(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
