package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductsResponse findProducts() {
        return ProductsResponse.from(productDao.findProducts());
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(productDao.findProductById(productId));
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
