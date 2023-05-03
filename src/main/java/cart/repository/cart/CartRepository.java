package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;

public interface CartRepository {

    boolean existCart(Member member);

    Cart findCartByMember(Member member);

    void createCart(Cart cart);

    Long saveCartItem(Cart cart);

    void deleteCartItem(Cart cart, Product product);
}
