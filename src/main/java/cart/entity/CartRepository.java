package cart.entity;

import java.util.List;

public interface CartRepository {
    Long save(Cart cart);

    Cart findById(Long id);

    List<Cart> findAll(Long memberId);

    void deleteById(Long id);

    void deleteByProductID(Long productId);
}
