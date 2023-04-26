package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductInMemoryDao implements ProductDao {

    private final List<ProductEntity> products; // 넣는 순서는 상관없으므로 동시성 문제가 없다.
    private final AtomicInteger sequence = new AtomicInteger(0); // 동시성 문제 해결

    public ProductInMemoryDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Integer insert(final ProductEntity productEntity) {
        final int id = sequence.incrementAndGet();
        ProductEntity productEntityWithId = new ProductEntity(
                id,
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getPrice()
        );

        products.add(productEntityWithId);
        return id;
    }

    @Override
    public void update(final ProductEntity productEntity) {
        final int index = products.indexOf(productEntity);
        products.set(index, productEntity);
    }

    @Override
    public void deleteById(final Integer id) {
        products.remove(select(id));
    }

    @Override
    public ProductEntity select(final Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
    }

    @Override
    public List<ProductEntity> findAll() {
        return products;
    }
}
