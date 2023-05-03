package cart.repository;

import cart.entity.Product;
import java.util.List;

public interface ShoppingCartRepository {

    void addProduct(long userId, long productId);

    void removeProduct(long userId, long productId);

    List<Product> findAllProduct(long userId);
}
