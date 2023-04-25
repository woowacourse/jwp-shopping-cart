package cart.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import cart.entiy.ProductEntity;

public class StubProductDao implements ProductDao {

    private final Map<Long, ProductEntity> productEntityMap = new HashMap<>();
    private final AtomicLong maxId = new AtomicLong();

    @Override
    public ProductEntity save(final ProductEntity productEntity) {
        final long currentId = maxId.incrementAndGet();

        final ProductEntity saved = new ProductEntity(currentId, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
        productEntityMap.put(currentId, saved);
        return saved;
    }

    @Override
    public Optional<ProductEntity> findById(final Long id) {
        final ProductEntity productEntity = productEntityMap.get(id);
        if (productEntity == null) {
            return Optional.empty();
        }
        return Optional.of(productEntity);
    }

    @Override
    public void deleteById(final Long id) {
        productEntityMap.remove(id);
    }
}
