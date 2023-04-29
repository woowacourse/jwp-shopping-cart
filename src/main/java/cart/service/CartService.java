package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.controller.dto.ProductRegisterRequest;
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

    public long save(ProductRegisterRequest productRegisterRequest) {
        ProductEntity productEntity = productRegisterRequest.toEntity();
        return productDao.insert(productEntity);
    }

    public void modifyById(ProductRegisterRequest productRegisterRequest, long id) {
        ProductEntity productEntity = productRegisterRequest.toEntityBy(id);
        productDao.update(productEntity);
    }

    public void removeById(long id) {
        productDao.delete(id);
    }
}
