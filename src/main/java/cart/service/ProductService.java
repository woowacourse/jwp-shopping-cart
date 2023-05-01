package cart.service;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import cart.response.ProductResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        final Map<Long, Product> savedProducts = productRepository.findAll();

        return toProductResponses(savedProducts);
    }

    private List<ProductResponse> toProductResponses(final Map<Long, Product> savedProducts) {
        final List<ProductResponse> result = new ArrayList<>();
        for (final Map.Entry<Long, Product> entry : savedProducts.entrySet()) {
            final Long id = entry.getKey();
            final Product product = entry.getValue();

            result.add(new ProductResponse(id, product.getName(), product.getPrice(), product.getImageUrl()));
        }

        result.sort((product1, product2) -> (int) (product1.getId() - product2.getId()));
        return List.copyOf(result);
    }

    public Long register(final ProductDto productDto) {
        final Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        return productRepository.insert(product);
    }

    public void updateProduct(final long id, final ProductDto productDto) {
        validateExistData(id);

        final Product newProduct = new Product(
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl()
        );

        productRepository.update(id, newProduct);
    }

    public void deleteProduct(final long id) {
        validateExistData(id);

        productRepository.delete(id);
    }

    private void validateExistData(final long id) {
        if (!productRepository.isExist(id)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
