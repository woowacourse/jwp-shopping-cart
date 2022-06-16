package woowacourse.shoppingcart.application;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        return productDao.findProducts().stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(productRequest);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId);
        return ProductResponse.of(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
