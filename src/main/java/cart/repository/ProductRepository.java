package cart.repository;

import cart.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
