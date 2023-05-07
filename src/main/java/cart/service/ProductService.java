package cart.service;

import cart.dao.product.ProductRepository;
import cart.domain.Id;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
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

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(Id.EMPTY_ID,
                new ProductName(request.getName()),
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

    public void removeMember(Long productId) {
        productRepository.removeProduct(productId);
    }

}
