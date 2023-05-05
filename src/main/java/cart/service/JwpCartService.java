package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.entity.Product;
import cart.entity.ProductRepository;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;

@Service
@Transactional(readOnly = true)
public class JwpCartService {

    private final ProductRepository productRepository;

    public JwpCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
            .stream()
            .map(productDto -> Product.of(
                productDto.getId(),
                productDto.getName(),
                productDto.getImgUrl(),
                productDto.getPrice()))
            .map(ProductResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void add(ProductRequest productRequest) {
        Product product = Product.of(
            null,
            productRequest.getName(),
            productRequest.getImgUrl(),
            productRequest.getPrice()
        );
        productRepository.save(product);
    }

    @Transactional
    public void updateById(ProductRequest productRequest, Long id) {
        Product product = Product.of(
            null,
            productRequest.getName(),
            productRequest.getImgUrl(),
            productRequest.getPrice()
        );
        productRepository.updateById(product, id);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
