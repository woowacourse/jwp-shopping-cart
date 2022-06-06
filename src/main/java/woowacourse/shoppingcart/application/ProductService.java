package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductServiceResponse;
import woowacourse.shoppingcart.dao.ProductDao;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductServiceResponse> findAllProducts() {
        return productDao.findAll()
                .stream()
                .map(ProductServiceResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public void validateStock(long productId, int purchasingQuantity) {
        int stock = productDao.findStockById(productId);
        if (stock < purchasingQuantity) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }
    }

    public void validateProductId(long productId) {
        if (!productDao.existsId(productId)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }
}
