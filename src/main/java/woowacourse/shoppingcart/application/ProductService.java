package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;
    private final ProductRepository productRepository;

    public ProductService(final ProductDao productDao,
            ProductRepository productRepository) {
        this.productDao = productDao;
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findProducts() {
        return productRepository.findProducts().stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl()))
                .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl());
        return productRepository.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productRepository.findById(productId);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl());
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
