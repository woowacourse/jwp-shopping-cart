package woowacourse.shoppingcart.domain.order;

import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetail {

    private final Long id;
    private final Quantity quantity;
    private final Product product;

    public OrderDetail(int quantity, Product product) {
        this(null, new Quantity(quantity), product);
    }

    public OrderDetail(Long id, Quantity quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity.getAmount();
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public Integer getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }
}
