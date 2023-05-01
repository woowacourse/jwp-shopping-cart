package cart.service;

import cart.entity.Product;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductService::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public long createProduct(final ProductRequest httpRequest) {
        final Product savedProduct = productRepository.save(toProduct(httpRequest));
        return savedProduct.getId();
    }

    public void updateProduct(final ProductRequest productRequest) {
        final int updatedCount = productRepository.update(toProduct(productRequest));
        validateProductNotFound(updatedCount);
    }

    private static void validateProductNotFound(final int updatedCount) {
        if (updatedCount == 0) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteById(final long id) {
        productRepository.deleteById(id);
    }

    private static ProductResponse toResponse(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice());
    }

    private static Product toProduct(final ProductRequest productRequest) {
        return new Product(productRequest.getId(), productRequest.getName(), productRequest.getImageUrl(),
                productRequest.getPrice());
    }
}
