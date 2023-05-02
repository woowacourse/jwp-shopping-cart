package cart.business;

import cart.business.domain.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCRUDService {

    private final ProductRepository productRepository;

    public ProductCRUDService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void create(Product product) {
        productRepository.findByName(product.getName())
                .ifPresent(ignored -> {throw new IllegalArgumentException("이미 동일한 이름을 가진 상품이 존재합니다.");});

        productRepository.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> readAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void update(Product product) {
        productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 상품에 대해서만 업데이트 할 수 있습니다."));

        productRepository.update(product);
    }

    @Transactional
    public void delete(Integer productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 상품에 대해서만 삭제할 수 있습니다."));

        productRepository.remove(productId);
    }
}
