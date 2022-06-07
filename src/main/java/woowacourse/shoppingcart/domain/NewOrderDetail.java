package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class NewOrderDetail {
    private final Product product;
    private final Quantity quantity;

    public NewOrderDetail(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
