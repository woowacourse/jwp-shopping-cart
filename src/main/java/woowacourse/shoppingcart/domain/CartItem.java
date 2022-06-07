package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;

public class CartItem {
    private Long id;
    private Product product;
    private Quantity quantity;

    public CartItem(Product product, Quantity quantity) {
        this(null, product, quantity);
    }

    public CartItem(Long id, Product product, Quantity quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public void checkSameProduct(CartItem newCartItem) {
        if (product.equals(newCartItem.product)) {
            throw new DuplicateProductInCartException();
        }
    }

    public long getProductId() {
        return product.getId();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
