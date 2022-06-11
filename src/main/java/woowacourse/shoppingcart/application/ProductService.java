package woowacourse.shoppingcart.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotExistProductException;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(NotExistProductException::new);
    }

    public void deleteProductById(final Long productId) {
        productDao.deleteById(productId);
    }
}
