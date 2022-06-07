package woowacourse.shoppingcart.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@Repository
public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findById(final Long productId) {
        return productDao.findById(productId);
    }

    public List<Product> findProductsOfPage(final int page, final int limit) {
        return productDao.findProductsOfPage(page, limit);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }
}
