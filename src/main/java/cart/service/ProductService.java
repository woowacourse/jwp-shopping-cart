package cart.service;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
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
