package cart.service;

import java.util.List;

import cart.domain.product.Product;
import org.springframework.stereotype.Service;

@Service
public class CartSearchService {

    public List<Product> findByEmail(final String email) {
        return List.of(
                new Product(1L, "odo", "url", 1),
                new Product(2L, "nunu", "url", 1)
        );
    }
}
