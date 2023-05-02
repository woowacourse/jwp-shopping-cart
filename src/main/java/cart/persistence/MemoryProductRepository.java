package cart.persistence;

import cart.business.ProductRepository;
import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
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
                        new ProductName(entry.getValue().getName()),
                        new ProductImage(entry.getValue().getUrl()),
                        new ProductPrice(entry.getValue().getPrice())))
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
    public Boolean findSameProductExist(Product product) {
        return store.containsValue(product);
    }
}
