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
        return productDao.findProducts()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));
        return new ProductResponse(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
