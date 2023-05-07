package cart.product.service;

import cart.product.domain.ImageUrl;
import cart.product.domain.Product;
import cart.product.domain.ProductCategory;
import cart.product.domain.ProductName;
import cart.product.domain.ProductPrice;
import cart.product.domain.ProductRepository;
import cart.product.service.dto.ProductCreationDto;
import cart.product.usecase.EnrollOneProductUseCase;
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
