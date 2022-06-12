package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.domain.product.Product;

public class CartItem {

    private final Long id;
    private final Quantity quantity;
    private final Product product;

    public CartItem(Long id, Quantity quantity, Product product) {
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

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }
}
