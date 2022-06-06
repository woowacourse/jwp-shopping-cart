package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        return productDao.findProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(productDao.findProductById(productId));
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
