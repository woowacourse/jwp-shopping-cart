package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findAllProducts() {
        List<ProductEntity> allProducts = productDao.findAll();
        return allProducts.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public long save(ProductRequest productRequest) {
        ProductEntity productEntity = productRequest.toEntity();
        return productDao.insert(productEntity);
    }

    public void modifyById(ProductRequest productRequest, long id) {
        ProductEntity productEntity = productRequest.toEntityBy(id);
        productDao.update(productEntity);
    }

    public void removeById(long id) {
        productDao.delete(id);
    }
}
