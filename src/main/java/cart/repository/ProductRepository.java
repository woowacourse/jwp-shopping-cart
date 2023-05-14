package cart.repository;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import cart.excpetion.product.DuplicateProductException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void create(Product product) {
        if (productDao.exitingProductName(product.getName())) {
            throw new DuplicateProductException("이미 존재하는 상품명입니다");
        }
        productDao.create(new ProductEntity(
                        product.getName(),
                        product.getImage(),
                        product.getPrice()
                )
        );
    }

    public List<Product> findAll() {
        final List<ProductEntity> allEntity = productDao.findAll();
        final List<Product> result = new ArrayList<>();
        for (ProductEntity productEntity : allEntity) {
            result.add(new Product(
                            productEntity.getId(),
                            productEntity.getName(),
                            productEntity.getImage(),
                            productEntity.getPrice()
                    )
            );
        }
        return result;
    }

    public Optional<Product> findBy(final int id) {
        final Optional<ProductEntity> productEntity = productDao.findBy(id);
        if (productEntity.isPresent()) {
            final ProductEntity entity = productEntity.get();
            return Optional.of(new Product(
                            entity.getId(),
                            entity.getName(),
                            entity.getImage(),
                            entity.getPrice()
                    )
            );
        }
        return Optional.empty();
    }

    public void update(final int id, final Product product) {
        productDao.update(
                new ProductEntity(
                        id,
                        product.getName(),
                        product.getImage(),
                        product.getPrice()
                )
        );
    }

    public void delete(final int id) {
        productDao.delete(id);
    }
}
