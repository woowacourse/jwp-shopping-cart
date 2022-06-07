package woowacourse.shoppingcart.domain;

public interface CartItemRepository {
    CartItems findByCustomer(long customerId);

    long addCartItem(long customerId, CartItem cartItem);

    CartItem findById(long id);

    void update(CartItem updateCartItem);
}
