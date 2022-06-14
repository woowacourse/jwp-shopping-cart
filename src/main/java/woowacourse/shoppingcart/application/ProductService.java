package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findProducts() {
        return productRepository.findProducts().stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    public Long addProduct(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl());
        return productRepository.save(product);
    }

    public ProductResponse findProductById(Long productId) {
        return productRepository.findById(productId)
                .map(this::toProduct)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));
    }

    private ProductResponse toProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl());
    }

    public void deleteProductById(Long productId) {
        productRepository.delete(productId);
    }
}
