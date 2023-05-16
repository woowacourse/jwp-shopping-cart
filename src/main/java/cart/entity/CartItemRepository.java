package cart.entity;

import java.util.List;

public interface CartItemRepository {
    Long save(CartItem cartItem);

    CartItem findById(Long id);

    List<CartItem> findAll(Long memberId);

    void deleteById(Long id);

    void deleteByProductID(Long productId);
}
