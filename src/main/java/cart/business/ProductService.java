package cart.business;

import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import cart.presentation.dto.ProductRequest;
import cart.presentation.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Integer create(ProductRequest request) {
        Product product = makeProductFromRequest(request);

        if (productRepository.findSameProductExist(product)) {
            throw new IllegalArgumentException("이미 동일한 상품이 존재합니다.");
        }

        return productRepository.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Transactional
    public Integer update(Integer id, ProductRequest request) {
        Product product = makeProductFromRequest(request);
        return productRepository.update(id, product);
    }

    @Transactional
    public Integer delete(Integer id) {
        return productRepository.remove(id);
    }

    private Product makeProductFromRequest(ProductRequest request) {
        return new Product(
                null,
                new ProductName(request.getName()),
                new ProductImage(request.getUrl()),
                new ProductPrice(request.getPrice())
        );
    }

    private Product makeProductFromResponse(ProductResponse response) {
        return new Product(
                response.getId(),
                new ProductName(response.getName()),
                new ProductImage(response.getUrl()),
                new ProductPrice(response.getPrice())
        );
    }
}
