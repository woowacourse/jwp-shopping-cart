package cart.service;

import cart.domain.Product;
import cart.domain.ProductRepository;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class JwpCartService {

    private final ProductRepository productRepository;

    public JwpCartService(ProductRepository productRepository) {
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
        ProductEntity productEntity = productRepository.save(product);
        return ProductDto.fromEntity(productEntity);
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
