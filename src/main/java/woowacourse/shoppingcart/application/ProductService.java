package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId)
            .orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public Product save(ProductRequest request) {
        return productDao.save(new Product(request.getName(), request.getPrice(), request.getImageUrl()));
    }
}
