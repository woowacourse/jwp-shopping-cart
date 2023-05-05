package cart.service;

import cart.dao.ProductRepository;
import cart.domain.Id;
import cart.domain.ImageUrl;
import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto saveProduct(ProductRequestDto request) {
        Product product = new Product(new ProductName(request.getName()),
                new ImageUrl(request.getImage()),
                new Price(request.getPrice()));
        Long productId = productRepository.addProduct(product);
        return new ProductDto(productId, product.getName(), request.getImage(), product.getPrice());
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return products.stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getImageUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductDto findProduct(Long id) {
        Product product = productRepository.getProduct(id);
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice());
    }

    public ProductDto updateProduct(Long productId, ProductRequestDto request) {
        Product product = new Product(new Id(productId),
                new ProductName(request.getName()),
                new ImageUrl(request.getImage()),
                new Price(request.getPrice()));
        productRepository.updateProduct(product);
        return new ProductDto(productId, product.getName(), product.getImageUrl(), product.getPrice());
    }

    public void deleteProduct(Long productId) {
        productRepository.removeProduct(productId);
    }

}
