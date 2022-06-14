package woowacourse.shoppingcart.domain.order;

import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetail {

    private final Long id;
    private final int quantity;
    private final Product product;

    public OrderDetail(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderDetail(Product product, int quantity) {
        this(null, product, quantity);
    }

    public int calculateCost() {
        return product.multiplePrice(quantity);
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
