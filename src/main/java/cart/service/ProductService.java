package cart.service;

import cart.domain.product.Product;
import cart.dto.product.ProductDto;
import cart.dto.product.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(ProductDto::fromEntity)
                .collect(toList());
    }

    @Transactional
    public ProductDto add(ProductRequestDto productRequestDto) {
        Product product = new Product(
                productRequestDto.getName(),
                productRequestDto.getImgUrl(),
                productRequestDto.getPrice()
        );
        ProductEntity entity = new ProductEntity(null, product.getName(), product.getImgUrl(), product.getPrice());
        ProductEntity savedProductentity = productRepository.save(entity);
        return ProductDto.fromEntity(savedProductentity);
    }

    @Transactional
    public ProductDto updateById(ProductRequestDto productRequestDto, Long id) {
        Product product = new Product(
                productRequestDto.getName(),
                productRequestDto.getImgUrl(),
                productRequestDto.getPrice()
        );
        ProductEntity productEntity = new ProductEntity(id, product.getName(), product.getImgUrl(), product.getPrice());
        productRepository.update(productEntity);
        return ProductDto.fromEntity(productEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
