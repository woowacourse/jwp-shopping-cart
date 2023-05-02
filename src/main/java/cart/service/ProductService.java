package cart.service;

import static java.util.stream.Collectors.toList;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto createProduct(String name, int price, String imageUrl) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
        ProductEntity productEntity = productRepository.save(product);
        return ProductDto.fromEntity(productEntity);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .collect(toList());
    }

    public void deleteById(Long id) {
        validateId(id);
        productRepository.deleteById(id);
    }

    public ProductDto updateProductById(Long id, String name, int price, String imageUrl) {
        validateId(id);
        ProductEntity productEntity = ProductEntity.builder()
                .id(id)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
        productRepository.update(productEntity);
        return ProductDto.fromEntity(productEntity);
    }

    @Transactional(readOnly = true)
    private void validateId(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("존재하지 않는 상품의 ID 입니다.");
        }
    }
}
