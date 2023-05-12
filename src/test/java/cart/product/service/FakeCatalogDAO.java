package cart.product.service;

import cart.catalog.dao.CatalogDAO;
import cart.catalog.domain.Name;
import cart.catalog.domain.Price;
import cart.catalog.domain.Product;

import java.util.List;

public class FakeCatalogDAO implements CatalogDAO {

    private final List<Product> products = List.of(
            new Product(new Name("망고"), "http://mango", new Price(1000)),
            new Product(new Name("에코"), "http://echo", new Price(2000))
    );

    @Override
    public long insert(final Product product) {
        return 0;
    }

    @Override
    public Product findByID(final long id) {
        final int index = (int) id - 1;
        return this.products.get(index);
    }

    @Override
    public Product findByName(final String name) {
        return null;
    }

    @Override
    public void deleteByID(final long id) {

    }

    @Override
    public void update(final Product product) {

    }

    @Override
    public List<Product> findAll() {
        return this.products;
    }
}
