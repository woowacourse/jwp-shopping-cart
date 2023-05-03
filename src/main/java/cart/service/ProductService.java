package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private static final String NOT_EXIST_PRODUCT = "해당 상품이 존재하지 않습니다.";

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void addProduct(ProductRequest productRequest) {
        productDao.save(productRequest.toEntity());
    }

    public List<ProductResponse> findProducts() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateProduct(ProductRequest productRequest) {
        validateExistence(productRequest.getId());

        productDao.update(productRequest.toEntity());
    }

    private void validateExistence(Long id) {
        if (!productDao.existById(id)) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT);
        }
    }

    public void deleteProduct(Long id) {
        validateExistence(id);

        productDao.delete(id);
    }
}
