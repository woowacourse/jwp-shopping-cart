package cart.service;

import cart.domain.Product;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductEditRequest;
import cart.dto.ProductsReadResponse;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductsReadResponse findAll() {
        List<Product> products = productRepository.findAll();
        return ProductsReadResponse.from(products);
    }

    @Transactional
    public void createProduct(final ProductCreateRequest productCreateRequest) {
        Product product = Product.from(productCreateRequest.getName(), productCreateRequest.getImgUrl(), productCreateRequest.getPrice());

        productRepository.add(product);
    }

    @Transactional
    public void editProduct(final Long id, final ProductEditRequest productEditRequest) {
        Product product = findProductById(id);
        product.edit(productEditRequest.getName(), productEditRequest.getImgUrl(), productEditRequest.getPrice());

        productRepository.update(product);
    }

    @Transactional
    public void deleteById(final Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}
