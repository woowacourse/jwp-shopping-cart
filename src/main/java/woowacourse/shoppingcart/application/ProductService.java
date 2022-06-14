package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse findById(final Long productId) {
        Product product = productRepository.selectById(productId);
        return ProductResponse.of(product);
    }

    public List<ProductResponse> findProductsOfPage(final int page, final int limit) {
        List<Product> productsOfPage = productRepository.selectProductsOfPage(page, limit);
        return productsOfPage.stream().map(ProductResponse::of).collect(Collectors.toList());
    }
}
