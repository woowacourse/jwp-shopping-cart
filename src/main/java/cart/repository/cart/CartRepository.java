package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;

import java.util.List;

public interface CartRepository {

    List<Cart> findAllByMember(Member member);

    void save(Cart cart);

    void delete(Member member, Product product);
}
