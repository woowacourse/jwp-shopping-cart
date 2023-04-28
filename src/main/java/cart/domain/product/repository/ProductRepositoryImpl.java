package cart.domain.product.repository;

import cart.domain.product.dao.ProductDao;
import cart.domain.product.entity.Product;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductDao productDao;

    public ProductRepositoryImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(final Product product) {
        return productDao.add(product);
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
