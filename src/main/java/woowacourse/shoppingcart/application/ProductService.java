package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.InvalidProductException;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.ProductDao;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        List<Product> products = productDao.findProducts();
        return ProductResponse.from(products);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = getById(productId);
        return ProductResponse.from(product);
    }

    private Product getById(Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
    }
}
