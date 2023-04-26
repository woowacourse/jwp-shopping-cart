package cart.persistence;

import cart.business.ProductRepository;
import cart.business.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryProductRepository implements ProductRepository {

    private final Map<Integer, Product> store = new ConcurrentHashMap<>();

    @Override
    public Integer insert(Product product) {
        store.put(product.getId(), product);
        return product.getId();
    }

    @Override
    public List<Product> findAll() {
        return store.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Product remove(Integer productId) {
        return store.remove(productId);
    }
}
