package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.dto.product.ProductsRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts(final ProductsRequest productsRequest) {
        final int page = productsRequest.getPage();
        final int limit = productsRequest.getLimit();
        return productDao.findProducts(limit, page);
    }

    public int findTotalCount() {
        return productDao.findTotalCount();
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
