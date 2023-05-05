package cart.service;

import cart.dao.ProductRepository;
import cart.domain.Id;
import cart.domain.ImageUrl;
import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;
import cart.dto.ProductResponse;
import cart.dto.ProductRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse saveProduct(ProductRequest request) {
        Product product = new Product(new ProductName(request.getName()),
                new ImageUrl(request.getImage()),
                new Price(request.getPrice()));
        Long productId = productRepository.addProduct(product);
        return new ProductResponse(productId, product.getName(), request.getImage(), product.getPrice());
    }

    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.getProduct(id);
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice());
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = new Product(new Id(productId),
                new ProductName(request.getName()),
                new ImageUrl(request.getImage()),
                new Price(request.getPrice()));
        productRepository.updateProduct(product);
        return new ProductResponse(productId, product.getName(), product.getImageUrl(), product.getPrice());
    }

    public void deleteProduct(Long productId) {
        productRepository.removeProduct(productId);
    }

}
