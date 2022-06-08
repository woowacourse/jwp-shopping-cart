package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.PageRequest;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts(final PageRequest pageRequest) {
        return productDao.findProducts(pageRequest.getLimit(), pageRequest.calculateOffset());
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public int findTotalCount() {
        return productDao.findTotalCount();
    }
}
