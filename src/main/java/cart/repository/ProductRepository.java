package cart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import cart.repository.exception.NoSuchIdException;

@Repository
public class ProductRepository {
    private static final int ZERO = 0;

    private final ProductDao productDao;

    public ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(final Product product) {
        productDao.insert(
                product.getName(),
                product.getImage(),
                product.getPrice()
        );
    }

    public void delete(final Integer id) {
        validateIdExists(productDao.deleteById(id));
    }

    public void update(final Product product) {
        validateIdExists(productDao.update(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        ));
    }

    private void validateIdExists(int affectedCount) {
        if (affectedCount == ZERO) {
            throw new NoSuchIdException();
        }
    }

    public List<Product> getAll() {
        return productDao.findAll().stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getPrice()
        );
    }
}
