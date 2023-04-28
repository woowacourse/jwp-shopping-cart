package cart.domain.product.service;

import cart.domain.product.dto.ProductRequest;
import cart.domain.product.dto.ProductResponse;
import cart.domain.product.entity.Product;
import cart.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse create(final ProductRequest productRequest) {
        final Product product = productRequest.makeProduct();
        final Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }
}
