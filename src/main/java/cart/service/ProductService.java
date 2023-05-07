package cart.service;

import cart.domain.cart.Product;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.ProductResponse;
import cart.persistence.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    public static final String PRODUCT_ID_ERROR_MESSAGE = "상품의 id가 올바르지 않습니다.";

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long createProduct(ProductRequest request) {
        Product product = request.toEntity();

        return productDao.insertAndGetKeyHolder(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productDao.findAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public ProductResponse getProduct(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_ID_ERROR_MESSAGE));

        return new ProductResponse(product);
    }

    public void updateProduct(ProductUpdateRequest request) {
        Product product = request.toEntity();

        productDao.update(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}
