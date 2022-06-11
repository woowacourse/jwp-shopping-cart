package woowacourse.shoppingcart.product.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.product.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.support.exception.ProductException;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        return productDao.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

    public ProductResponse findProductById(final long productId) {
        final Product product = productDao.findById(productId)
                .orElseThrow(() -> new ProductException(ProductExceptionCode.NO_SUCH_PRODUCT_EXIST));
        return ProductResponse.of(product);
    }
}
