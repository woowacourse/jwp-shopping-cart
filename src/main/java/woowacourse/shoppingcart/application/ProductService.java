package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long add(Product product) {
        return productDao.save(product);
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Product findProductById(Long productId) {
        return validateExistProduct(productDao.findProductById(productId));
    }

    @Transactional
    public void deleteProductById(Long productId) {
        productDao.delete(productId);
    }

    private Product validateExistProduct(Optional<Product> product) {
        return product.orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }
}
