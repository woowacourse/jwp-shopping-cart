package cart.domain.product.service;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductResponseDto;
import cart.domain.product.usecase.FindAllProductsUseCase;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FindAllProductService implements FindAllProductsUseCase {
    private final ProductRepository productRepository;

    public FindAllProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        final List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }
}
