package cart.service;

import cart.domain.Product;
import cart.domain.Products;
import cart.dto.request.RequestCreateProductDto;
import cart.dto.request.RequestUpdateProductDto;
import cart.repository.CartRepository;
import cart.repository.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final CartRepository cartRepository;

    @Autowired
    public ProductService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return cartRepository.getAllProductsWithId();
    }

    @Transactional
    public void addNewProduct(final RequestCreateProductDto requestCreateProductDto) {
        final Product product = new Product(requestCreateProductDto.getName(), requestCreateProductDto.getPrice(), requestCreateProductDto.getImage());

        cartRepository.insertNewProduct(product);
    }

    @Transactional
    public void update(final Long productId, final RequestUpdateProductDto requestUpdateProductDto) {
        final Product product = cartRepository.getProductById(productId);
        final Product updatedProduct = new Product(
                requestUpdateProductDto.getName().orElse(product.getName()),
                requestUpdateProductDto.getPrice().orElse(product.getPrice()),
                requestUpdateProductDto.getImage().orElse(product.getImage())
        );

        cartRepository.updateProduct(productId, updatedProduct);
    }

    @Transactional
    public void delete(final Long productId) {
        final Products products = cartRepository.getAllProducts();
        final Product product = cartRepository.getProductById(productId);
        products.delete(product);

        cartRepository.deleteProduct(productId);
    }
}
