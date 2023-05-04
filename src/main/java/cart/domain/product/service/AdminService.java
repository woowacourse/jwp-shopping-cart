package cart.domain.product.service;

import cart.domain.product.ImageUrl;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
import cart.domain.product.ProductRepository;
import cart.domain.product.service.dto.ProductCreationDto;
import cart.domain.product.service.dto.ProductDto;
import cart.domain.product.service.dto.ProductModificationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AdminService {
    private final ProductRepository productRepository;

    public AdminService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long save(final ProductCreationDto productDto) {
        final Product product = new Product(
                ProductName.from(productDto.getName()),
                ProductPrice.from(productDto.getPrice()),
                ProductCategory.valueOf(productDto.getCategory()),
                ImageUrl.from(productDto.getImageUrl())
        );

        return productRepository.save(product);
    }

    @Transactional
    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto update(final ProductModificationDto productDto) {
        final Product product = productDto.toProduct();
        productRepository.update(product);

        return ProductDto.from(product);
    }
}
