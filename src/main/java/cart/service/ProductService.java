package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto createProduct(String name, int price, String imageUrl) {
        long productId = productRepository.save(name, price, imageUrl);
        return new ProductDto(productId, name, price, imageUrl);
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productEntity -> new ProductDto(productEntity.getId(), productEntity.getName(),
                        productEntity.getPrice(), productEntity.getImageUrl()))
                .collect(toList());
    }

    public void deleteById(Long id) {
        validateId(id);
        productRepository.deleteById(id);
    }

    public void updateProductById(Long id, String name, int price, String imageUrl) {
        validateId(id);
        productRepository.update(new ProductEntity(id, name, price, imageUrl));
    }

    private void validateId(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("존재하지 않는 상품의 ID 입니다.");
        }
    }
}
