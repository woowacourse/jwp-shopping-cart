package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProductsOfPage(final int pageNumber, final int limit) {
        Products products = new Products(productDao.findAll());
        return products.calculatePage(pageNumber, limit);
    }

    public Product findProductById(final Long productId) {
        return productDao.findById(productId);
    }
}
