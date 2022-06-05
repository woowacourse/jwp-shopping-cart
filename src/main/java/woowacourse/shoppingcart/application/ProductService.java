package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Service
public class ProductService {

    private final ProductDao productDao;

    public List<Product> findProducts() {
        return productDao.findAll();
    }

    public Product findProductById(final Long productId) {
        return productDao.findById(productId);
    }
}

