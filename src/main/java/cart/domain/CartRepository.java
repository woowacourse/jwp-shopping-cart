package cart.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository {

    Cart save(Cart cart);

    Cart getCartByMemberId(Long id);
}
