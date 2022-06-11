package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductCountLimit;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts(int page, int limit) {
        ProductCountLimit productCountLimit = new ProductCountLimit(limit);
        int offset = new Page(page).calculateOffset(productCountLimit.getValue());
        return productDao.findProducts(offset, limit)
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public int getTotalProductCount() {
        return productDao.getTotalCount();
    }

    public void validateStock(long productId, int purchasingQuantity) {
        Product product = productDao.findById(productId);
        if (product.hasLowerStock(purchasingQuantity)) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }
    }

    public void validateProductId(long productId) {
        if (!productDao.checkIdExistence(productId)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }
}
