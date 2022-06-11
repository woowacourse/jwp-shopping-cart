package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(ProductRequest request) {
        return productDao.save(request.toEntity());
    }

    public Product findProductById(long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(long productId) {
        if (productDao.delete(productId) == 0) {
            throw new ProductNotFoundException();
        }
    }
}
