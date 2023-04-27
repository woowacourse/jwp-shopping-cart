package cart.domain.product.service;

import cart.domain.product.Product;
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

    public AdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long save(ProductCreationDto productDto) {
        Product product = productDto.toProduct();

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto update(ProductModificationDto productDto) {
        Product product = productDto.toProduct();
        productRepository.update(product);

        return ProductDto.from(product);
    }
}
