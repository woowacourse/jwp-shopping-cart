package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetail {
    private final Product product;
    private final Quantity quantity;

    public OrderDetail(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
