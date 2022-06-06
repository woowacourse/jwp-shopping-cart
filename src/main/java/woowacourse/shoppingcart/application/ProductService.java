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

    @Transactional(readOnly = true)
    public boolean isImpossibleQuantity(long productId, int purchasingQuantity) {
        int stock = productDao.findStockById(productId);
        return stock < purchasingQuantity;
    }
}
