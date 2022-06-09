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

    public Product selectById(final Long productId) {
        return productDao.selectById(productId);
    }

    public List<Product> selectProductsOfPage(final int page, final int limit) {
        return productDao.selectProductsOfPage(page, limit);
    }

    public List<Product> selectAll() {
        return productDao.selectAll();
    }
}
