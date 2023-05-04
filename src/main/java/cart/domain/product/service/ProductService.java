package cart.domain.product.service;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        final List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}
