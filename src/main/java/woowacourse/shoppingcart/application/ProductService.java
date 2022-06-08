package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.dto.ProductRequest;

@Service
public class ProductService {

    private static final Long DEFAULT_PAGE = 1L;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public ProductsResponse findProducts(Long size, Long page) {
        return new ProductsResponse(productDao.findProducts((page - DEFAULT_PAGE) * size, size));
    }

    @Transactional(readOnly = true)
    public ProductsResponse findAllProducts() {
        return new ProductsResponse(productDao.findAllProducts());
    }

    @Transactional
    public Product addProduct(ProductRequest productRequest) {
        return productDao.save(new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
    }

    @Transactional(readOnly = true)
    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
