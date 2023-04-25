package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.ProductDto;
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
}
