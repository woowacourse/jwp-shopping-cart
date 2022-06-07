package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductsRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts(final ProductsRequest productsRequest) {
        final List<Product> products = productDao.findProducts();
        final int page = productsRequest.getPage();
        final int limit = productsRequest.getLimit();

        return products.stream()
                .filter(getProductInRange(page, limit))
                .collect(Collectors.toList());
    }

    private Predicate<Product> getProductInRange(int page, int limit) {
        return product -> (long) (page - 1) * limit < product.getId()
                && product.getId() <= (long) page * limit;
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
