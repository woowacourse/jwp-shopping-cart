package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductDto;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findProducts() {
        final List<Product> products = productRepository.findProducts();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Long addProduct(final ProductDto productDto) {
        final Product product = ProductDto.toProduct(productDto);
        return productRepository.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(productRepository.findProductById(productId));
    }

    public void deleteProductById(final Long productId) {
        productRepository.deleteProductById(productId);
    }
}
