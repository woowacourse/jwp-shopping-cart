package cart.service;

import cart.domain.product.Product;
import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductEditRequestDto;
import cart.dto.ProductsResponseDto;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductsResponseDto findAll() {
        List<Product> products = productRepository.findAll();
        return ProductsResponseDto.from(products);
    }

    @Transactional
    public void createProduct(final ProductCreateRequestDto productCreateRequestDto) {
        Product product = Product.from(productCreateRequestDto.getName(), productCreateRequestDto.getImgUrl(), productCreateRequestDto.getPrice());

        productRepository.add(product);
    }

    @Transactional
    public void editProduct(final Long id, final ProductEditRequestDto productEditRequestDto) {
        Product product = findProductById(id);
        product.edit(productEditRequestDto.getName(), productEditRequestDto.getImgUrl(), productEditRequestDto.getPrice());

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
