package cart.domain.product.service;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductModificationDto;
import cart.domain.product.service.dto.ProductResponseDto;
import cart.domain.product.usecase.FixProductInfoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FixProductInfoService implements FixProductInfoUseCase {
    private final ProductRepository productRepository;

    public FixProductInfoService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto fixProductInfo(final ProductModificationDto productDto) {
        final Product product = productDto.toProduct();
        productRepository.update(product);

        return ProductResponseDto.from(product);
    }
}
