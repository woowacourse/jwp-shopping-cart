package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.PageRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findByPage(final PageRequest pageRequest) {
        List<Product> products = productDao.findByPage(pageRequest.getLimit(), pageRequest.calculateOffset());
        if (products.isEmpty()) {
            throw new InvalidProductException("상품 정보가 존재하지 않습니다.");
        }
        return products;
    }

    public Product findById(final Long productId) {
        return productDao.findById(productId);
    }

    public int findTotalCount() {
        return productDao.findTotalCount();
    }
}
