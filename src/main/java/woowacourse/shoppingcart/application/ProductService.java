package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductResponses;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long addProduct(ProductRequest productRequest) {
        Product product = productDao.save(productRequest.toProduct());
        return product.getId();
    }

    public ProductResponses findProducts(int size, int page) {
        List<Product> products = productDao.findProducts(size, size * (page - 1));
        return ProductResponses.from(products);
    }

    public ProductResponse findProductById(Long productId) {
        Product product = productDao.findProductById(productId);
        return ProductResponse.from(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductById(Long productId) {
        productDao.delete(productId);
    }
}
