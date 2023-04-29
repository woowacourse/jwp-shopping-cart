package cart.persistence;

import cart.business.ProductRepository;
import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryProductRepository implements ProductRepository {

    private final Map<Integer, Product> store = new ConcurrentHashMap<>();
    private int sequence = 1;

    @Override
    public Integer insert(Product product) {
        store.put(sequence, product);
        return sequence++;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return Optional.ofNullable(store.get(productId));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return store.values()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Product> findAll() {
        return store.entrySet()
                .stream()
                .map(entry -> new Product(entry.getKey(),
                        new ProductName(entry.getValue().getName()),
                        new ProductImage(entry.getValue().getUrl()),
                        new ProductPrice(entry.getValue().getPrice())))
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        return store.replace(product.getId(), product);
    }

    @Override
    public Product remove(Integer productId) {
        return store.remove(productId);
    }
}
