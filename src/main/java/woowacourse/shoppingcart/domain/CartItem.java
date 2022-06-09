package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartItem {

    private Long id;
    private Product product;
    private Quantity quantity;

    private CartItem() {
    }

    public CartItem(final Product product, final int quantity) {
        this(null, product, quantity);
    }

    public CartItem(final Long id, final Product product, final int quantity) {
        validateProductIsAvailable(product, quantity);
        this.id = id;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    private void validateProductIsAvailable(Product product, int quantity) {
        if (!product.isStockAvailable(quantity)) {
            throw new InvalidCartItemException(String.format("해당 상품은 %d개 남아있습니다", product.getStock()));
        }
    }

    public void changeQuantity(int quantity) {
        validateProductIsAvailable(product, quantity);
        this.quantity = new Quantity(quantity);
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
