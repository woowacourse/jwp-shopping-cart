package cart.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.repository.exception.NoSuchIdException;

@Repository
public class ProductRepository {
    private static final int ZERO = 0;

    private final ProductDao productDao;

    public ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(final String name, final String image, final Long price) {
        productDao.insert(name, image, price);
    }

    public void delete(final Integer id) {
        validateIdExists(productDao.deleteById(id));
    }

    public void update(final Integer id, final String name, final String image, final Long price) {
        validateIdExists(productDao.update(id, name, image, price));
    }

    private void validateIdExists(int affectedCount) {
        if (affectedCount == ZERO) {
            throw new NoSuchIdException();
        }
    }

    public List<ProductEntity> getAll() {
        return productDao.findAll();
    }
}
