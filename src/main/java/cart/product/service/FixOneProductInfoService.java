package cart.product.service;

import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.product.service.dto.ProductModificationDto;
import cart.product.service.dto.ProductResponseDto;
import cart.product.usecase.FixOneProductInfoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FixOneProductInfoService implements FixOneProductInfoUseCase {
    private final ProductRepository productRepository;

    public FixOneProductInfoService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto fixSingleProductInfo(final ProductModificationDto productDto) {
        final Product product = productDto.toProduct();
        productRepository.update(product);

        return ProductResponseDto.from(product);
    }
}
