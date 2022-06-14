package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.domain.product.Product;

public class CartItem {

    private Long id;
    private Product product;
    private Quantity quantity;

    private CartItem() {
    }

    public CartItem(Long id, Product product, Quantity quantity) {
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

    public CartItem update(final int updateQuantity) {
        return new CartItem(id, product, new Quantity(updateQuantity));
    }
}
