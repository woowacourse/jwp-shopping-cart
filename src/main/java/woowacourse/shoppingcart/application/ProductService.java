package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProductsOfPage(final int pageNumber, final int limit) {
        Products products = new Products(productDao.findAll());
        return products.calculatePage(pageNumber, limit).stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.of(productDao.findById(productId));
    }
}
