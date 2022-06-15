package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.exception.product.ShoppingCartNotFoundProductException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(ShoppingCartNotFoundProductException::new);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
