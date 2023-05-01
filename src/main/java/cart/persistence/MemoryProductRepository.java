package cart.persistence;

import cart.business.ProductRepository;
import cart.business.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return store.values()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findAny();
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
        return store.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Product update(Product product) {
        Integer productKey = findKeyWithPredicate(entry -> entry.getValue().equals(product));
        return store.replace(productKey, product);
    }

    @Override
    public Product remove(Integer productId) {
        Integer productKey = findKeyWithPredicate(entry -> entry.getValue().getId().equals(productId));
        return store.remove(productKey);
    }

    private Integer findKeyWithPredicate(Predicate<Map.Entry<Integer, Product>> predicate) {
       return store.entrySet()
                .stream()
                .filter(predicate)
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
    }
}
