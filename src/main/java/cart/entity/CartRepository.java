package cart.entity;

import java.util.List;

public interface CartRepository {
    Long save(Cart cart);

    List<Cart> findAll();

    void deleteById(Long id);
}
