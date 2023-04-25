package cart.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductsDao {
    void create(final String name, final int price, final String image);
    List<Product> readAll();
    void update(final Product product);
    void delete(final long id);
}
