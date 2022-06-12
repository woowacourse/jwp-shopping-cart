package woowacourse.shoppingcart.entity;

import java.util.Objects;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemEntity {

    private final Long customerId;
    private final CartItem cartItem;

    public CartItemEntity(Long customerId, CartItem cartItem) {
        this.customerId = customerId;
        this.cartItem = cartItem;
    }

    public CartItemEntity(Customer customer, CartItem cartItem) {
        this(customer.getId(), cartItem);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return cartItem.getProductId();
    }

    public int getQuantity() {
        return cartItem.getQuantity();
    }

    public CartItem toDomain() {
        return cartItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemEntity that = (CartItemEntity) o;
        return Objects.equals(customerId, that.customerId)
                && Objects.equals(cartItem, that.cartItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, cartItem);
    }
}
