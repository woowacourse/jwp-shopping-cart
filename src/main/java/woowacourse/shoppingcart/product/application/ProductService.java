package woowacourse.shoppingcart.product.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findAll();
    }

    public Product findProductById(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(InvalidProductException::new);
    }
}
