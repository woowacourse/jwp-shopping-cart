package cart.repository;

import java.util.List;

import cart.domain.product.Product;
import cart.domain.user.Email;
import cart.domain.user.User;

public interface CartRepository {

    void save(User user, Product product);

    List<Product> findByEmail(Email email);

    void deleteById(Long id);
}
