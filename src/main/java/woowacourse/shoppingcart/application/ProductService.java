package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.ProductServiceResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DataNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductSaveServiceRequest request) {
        return productDao.save(request.toEntity());
    }

    public ProductServiceResponse findById(final Long productId) {
        final Product product = findProduct(productId);
        return ProductServiceResponse.from(product);
    }

    public List<ProductServiceResponse> findAll() {
        final List<Product> products = productDao.findProducts();
        return products.stream()
                .map(ProductServiceResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteById(final Long productId) {
        findProduct(productId);
        productDao.delete(productId);
    }

    private Product findProduct(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(DataNotFoundException::new);
    }
}
