package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;

import java.util.List;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getUrl()));
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
