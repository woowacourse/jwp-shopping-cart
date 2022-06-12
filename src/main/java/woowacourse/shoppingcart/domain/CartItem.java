package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.common.exception.InvalidRequestException;

public class CartItem {

    private static final int MIN_QUANTITY = 0;
    private static final int INITIAL_QUANTITY = 1;

    private final Product product;
    private final int quantity;

    public CartItem(Product product, int quantity) {
        validate(quantity);
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Product product) {
        this(product, INITIAL_QUANTITY);
    }

    private void validate(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidRequestException("상품의 수량은 음수가 될 수 없습니다.");
        }
    }

    public boolean hasQuantityOverZero() {
        return quantity > 0;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity
                && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "CartItem{" + "product=" + product + ", quantity=" + quantity + '}';
    }
}
