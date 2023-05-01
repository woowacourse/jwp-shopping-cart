package cart.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;

@Repository
public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(final String name, final String image, final Long price) {
        productDao.insert(name, image, price);
    }

    public void delete(final Integer id) {
        productDao.deleteById(id);
    }

    public void update(final Integer id, final String name, final String image, final Long price) {
        ProductEntity entity = productDao.select(id);
        productDao.update(entity.getId(), name, image, price);
    }

    public List<ProductEntity> getAll() {
        return productDao.findAll();
    }
}
