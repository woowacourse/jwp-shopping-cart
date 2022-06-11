package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductSaveRequest request) {
        Long id = productDao.save(request.toEntity());
        return id;
    }

    public ProductResponse findById(Long saveId) {
        Product product = productDao.findById(saveId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        return new ProductResponse(product);
    }

    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(it -> new ProductResponse(it.getId(), it.getName(), it.getPrice(), it.getImageUrl()))
                .collect(Collectors.toList());
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }
}
