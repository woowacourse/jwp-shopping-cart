package cart.domain.product.service;

import cart.domain.product.ImageUrl;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductCreationDto;
import cart.domain.product.usecase.EnrollOneProductUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EnrollOneProductService implements EnrollOneProductUseCase {
    private final ProductRepository productRepository;

    public EnrollOneProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Long enroll(final ProductCreationDto productDto) {
        final Product product = new Product(
                ProductName.from(productDto.getName()),
                ProductPrice.from(productDto.getPrice()),
                ProductCategory.valueOf(productDto.getCategory()),
                ImageUrl.from(productDto.getImageUrl())
        );

        return productRepository.save(product);
    }
}
