package cart.domain.product.service;

import cart.domain.product.*;
import cart.domain.product.service.dto.ProductCreationDto;
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
        Product product = new Product(
                ProductName.from(productDto.getName()),
                ProductPrice.from(productDto.getPrice()),
                ProductCategory.valueOf(productDto.getCategory()),
                ImageUrl.from(productDto.getImageUrl())
        );

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public int update(ProductModificationDto productDto) {
        Product product = new Product(
                ProductName.from(productDto.getName()),
                ProductPrice.from(productDto.getPrice()),
                ProductCategory.valueOf(productDto.getCategory()),
                ImageUrl.from(productDto.getImageUrl()),
                productDto.getId()
        );

        return productRepository.update(product);
    }
}
