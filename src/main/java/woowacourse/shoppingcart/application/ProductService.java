package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.dto.ProductRequest;

@Service
public class ProductService {

    private static final Long DEFAULT_PAGE = 1L;

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public ProductsResponse findProducts(Long size, Long page) {
        return ProductsResponse.from(productDao.findProducts((page - DEFAULT_PAGE) * size, size));
    }

    @Transactional(readOnly = true)
    public ProductsResponse findAllProducts() {
        return new ProductsResponse(productDao.findAllProducts());
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productDao.save(new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
