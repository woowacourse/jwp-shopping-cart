package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;

public class CartItem {
    private Long id;
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this(null, product, quantity);
    }

    public CartItem(Long id, Product product, int quantity) {
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
        return quantity;
    }

    public void checkSameProduct(CartItem newCartItem) {
        if (product.equals(newCartItem.product)) {
            throw new DuplicateProductInCartException();
        }
    }
}
