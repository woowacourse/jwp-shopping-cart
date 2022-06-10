package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public Long addProduct(final Product product) {
        return productRepository.save(product);
    }

    public Product findProductById(final Long productId) {
        return productRepository.findById(productId);
    }

    public void deleteProductById(final Long productId) {
        productRepository.delete(productId);
    }
}
