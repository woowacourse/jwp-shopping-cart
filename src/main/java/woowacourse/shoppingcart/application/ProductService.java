package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.global.exception.InvalidCustomerException;
import woowacourse.global.exception.InvalidProductException;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dao.ProductDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductSaveRequest request) {
        validateProductName(request.getName());
        return productDao.save(request.toEntity());
    }

    private void validateProductName(String name) {
        if (productDao.existByName(name)) {
            throw new InvalidProductException("[ERROR] 이미 존재하는 상품입니다.");
        }
    }

    public ProductResponse findById(Long id) {
        return new ProductResponse(productDao.findById(id)
                .orElseThrow(() -> new InvalidProductException("[ERROR] ID가 존재하지 않습니다.")));
    }

    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
