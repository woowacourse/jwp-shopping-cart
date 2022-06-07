package woowacourse.shoppingcart.domain;

public interface CartItemRepository {
    CartItems findByCustomerId(long customerId);

    long addCartItem(long customerId, CartItem cartItem);

    CartItem findById(long id);
}
