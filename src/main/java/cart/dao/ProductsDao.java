package cart.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductsDao {
    Long create(final String name, final int price, final String image);

    List<Product> readAll();

    Product findById(final long id);

    void update(final Product product, final String nameToUpdate, final int priceToUpdate, final String imageUrlToUpdate);

    void delete(final long id);
}
