package cart.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import cart.entity.ProductEntity;
import cart.repository.ProductDto;

@Repository
public class ProductInMemoryDao implements ProductDao {

    private final List<ProductEntity> products; // 넣는 순서는 상관없으므로 동시성 문제가 없다.
    private final AtomicInteger sequence = new AtomicInteger(0); // 동시성 문제 해결

    public ProductInMemoryDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Integer insert(final ProductDto productEntity) {
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
    public void update(final Integer id, final ProductDto productDto) {
        final int index = products.indexOf(select(id));
        products.set(index, new ProductEntity(
                        id,
                        productDto.getName(),
                        productDto.getImage(),
                        productDto.getPrice()
                )
        );
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
