package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long save(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        return productDao.save(product);
    }

    public int countAll() {
        return productDao.countAll();
    }

    public List<ProductResponse> findProducts(int page, int limit) {
        return productDao.findProductsByPaging(page, limit)
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
