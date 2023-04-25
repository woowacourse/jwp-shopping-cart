package cart.service;

import cart.domain.Product;
import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductsResponseDto;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductsResponseDto findAll() {
        List<Product> products = productRepository.findAll();
        return ProductsResponseDto.from(products);
    }

    @Transactional
    public void createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product product = Product.from(productCreateRequestDto.getName(), productCreateRequestDto.getImgUrl(), productCreateRequestDto.getPrice());
        productRepository.add(product);
    }

}
