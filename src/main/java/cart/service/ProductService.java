package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public long createProduct(String name, int price, String imageUrl) {
        return productRepository.save(name, price, imageUrl);
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productEntity -> new ProductDto(productEntity.getId(), productEntity.getName(),
                        productEntity.getPrice(), productEntity.getImageUrl()))
                .collect(toList());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProductById(Long id, String name, int price, String imageUrl) {
        productRepository.updateProduct(new ProductEntity(id, name, price, imageUrl));
    }
}
