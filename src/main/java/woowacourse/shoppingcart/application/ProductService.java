package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductsResponse findProducts() {
        final List<Product> products = productDao.findAll();

        return ProductsResponse.of(products);
    }

    public Product addProduct(final Product product) {
        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findById(productId)
                .orElseThrow(InvalidProductException::new);

        return ProductResponse.of(product);
    }

    public int deleteProductById(final Long productId) {
        return productDao.delete(productId);
    }
}
