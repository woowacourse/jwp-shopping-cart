package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddProductRequest;

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

    public long addProduct(final AddProductRequest request) {
        return productDao.save(new Product(request.getName(), request.getPrice(), request.getImageUrl()));
    }

    public Product findProductById(final long productId) {
        return productDao.findById(productId);
    }

    public void deleteProductById(final long productId) {
        productDao.delete(productId);
    }
}
