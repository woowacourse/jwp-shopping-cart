package cart.dao.product;

import cart.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeProductDao implements ProductDao {

    private final List<Product> products = new ArrayList<>();

    @Override
    public long insert(final Product product) {
        products.add(product);
        return product.getId();
    }

    @Override
    public Optional<Product> findByID(final long id) {
        return products.stream()
            .filter(product -> product.getId() == id)
            .findFirst();
    }

    @Override
    public void deleteByID(final long id) {
        products.removeIf(product -> product.getId() == id);
    }

    @Override
    public void update(final Product product) {
        products.stream()
            .filter(it -> it.getId() == product.getId())
            .findFirst()
            .ifPresent(it -> products.set(products.indexOf(it), product));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }
}
