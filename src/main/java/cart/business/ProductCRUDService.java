package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCRUDService {

    private final ProductRepository productRepository;

    public ProductCRUDService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void create(Product product) {
        Optional<Product> foundProduct = productRepository.findByName(product.getName());

        if (foundProduct.isPresent()) {
            throw new IllegalArgumentException("이미 동일한 이름을 가진 상품이 존재합니다.");
        }
        productRepository.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> readAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void update(Product product) {
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("존재하는 상품에 대해서만 업데이트 할 수 있습니다.");
        }
        productRepository.update(product);
    }

    @Transactional
    public void delete(Integer productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("존재하는 상품에 대해서만 삭제할 수 있습니다.");
        }
        productRepository.remove(productId);
    }
}
