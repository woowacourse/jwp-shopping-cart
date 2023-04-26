package cart.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import cart.domain.Product;

public class StubProductRepository implements ProductRepository {

    private final Map<Long, Product> productMap = new HashMap<>();
    private final AtomicLong maxId = new AtomicLong();

    @Override
    public Product save(final Product product) {
        final long currentId = maxId.incrementAndGet();

        final Product saved = new Product(currentId, product);
        productMap.put(currentId, saved);
        return saved;
    }

    @Override
    public Product update(final Product product) {
        final Long id = product.getProductId().getValue();
        if (id == null || productMap.get(id) == null) {
            return product;
        }
        productMap.put(id, product);
        return product;
    }

    @Override
    public Optional<Product> findById(final Long id) {
        final Product product = productMap.get(id);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(product);
    }

    @Override
    public void deleteById(final Long id) {
        productMap.remove(id);
    }
}
