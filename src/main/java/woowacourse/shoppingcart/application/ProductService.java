package woowacourse.shoppingcart.application;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts()
                .stream()
                .sorted(Comparator.comparingLong(Product::getId).reversed())
                .collect(Collectors.toList());
    }

    public Product findProduct(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));
    }
}
