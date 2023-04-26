package cart.persistence;

import cart.business.ProductRepository;
import cart.business.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    public List<Product> findAll() {
        return store.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Product findById(Integer id) {
        return store.get(id);
    }

    @Override
    public Integer findIdByProduct(Product product) {
        return store.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == product)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 ID에 맞는 상품을 찾지 못했습니다."))
                .getKey();
    }

    @Override
    public Product remove(Integer productId) {
        return store.remove(productId);
    }

}
