package cart.persistence;

import cart.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryProductRepository implements ProductRepository {

    private final Map<Integer, Product> store = new ConcurrentHashMap<>();
    private static int sequence = 1;

    @Override
    public synchronized Integer insert(Product product) {
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
