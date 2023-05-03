package cart.repository;

import java.util.List;

import cart.domain.product.Product;
import cart.domain.user.Email;

public interface CartRepository {

    void save(String email, Long productId);

    List<Product> findByEmail(Email email);

    void deleteById(Long id);
}
