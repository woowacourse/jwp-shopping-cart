package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductRequest;

@Service
public class ProductService {

    private static final Long DEFAULT_PAGE = 1L;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public Products findProducts(Long size, Long page) {
        return productDao.findProducts((page - DEFAULT_PAGE) * size, size);
    }

    @Transactional(readOnly = true)
    public Products findAllProducts() {
        return productDao.findAllProducts();
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
