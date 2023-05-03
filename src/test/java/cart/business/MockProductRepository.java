package cart.business;

import cart.entity.Product;
import cart.persistence.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MockProductRepository implements ProductRepository {

    private final Map<Integer, Product> store = new ConcurrentHashMap<>();
    private int sequence = 1;

    @Override
    public Integer insert(Product product) {
        store.put(sequence, product);
        return sequence++;
    }

    @Override
    public List<Product> findAll() {
        return store.entrySet()
                .stream()
                .map(entry -> new Product(entry.getKey(),
                        (entry.getValue().getName()),
                        (entry.getValue().getUrl()),
                        (entry.getValue().getPrice())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer update(Integer id, Product product) {
        store.replace(id, product);
        return id;
    }

    @Override
    public Integer remove(Integer id) {
        store.remove(id);
        return id;
    }

    @Override
    public void findSameProductExist(Product product) {
        if (store.containsValue(product)) {
            throw new IllegalArgumentException("동일한 상품이 존재합니다");
        }
    }
}
