package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void perform(Product product) {
        List<Product> allProducts = productRepository.findAll();

        Optional<Product> foundProduct = allProducts.stream()
                .filter(eachProduct -> eachProduct.getProductPrice().equals(product.getProductPrice()))
                .filter(eachProduct -> eachProduct.getName().equals(product.getName()))
                .findAny();

        if (foundProduct.isPresent()) {
            throw new IllegalArgumentException("이미 동일한 상품이 존재합니다.");
        }
        productRepository.insert(product);
    }
}
