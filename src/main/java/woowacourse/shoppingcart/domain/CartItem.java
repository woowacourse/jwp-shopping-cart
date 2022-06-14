package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.badrequest.InvalidCartItemQuantityException;

public class CartItem {

    private final Long id;
    private final Product product;
    private final int quantity;

    public CartItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        validateQuantity();
    }

    private void validateQuantity() {
        if (quantity < 0) {
            throw new InvalidCartItemQuantityException();
        }
    }

    public boolean hasProductById(Long productId) {
        return product.hasId(productId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return product.getName();
    }

    public Long getProductId() {
        return product.getId();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem updateQuantity(int quantity) {
        return new CartItem(id, product, quantity);
    }
}
