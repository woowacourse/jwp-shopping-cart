package woowacourse.shoppingcart.service;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.service.dto.ProductCreateServiceRequest;

public interface ProductService {
    Product create(ProductCreateServiceRequest productCreateServiceRequest);

    List<Product> findAllWithPage(int page, int size);

    Product findById(long id);

    void deleteById(long productId);
    
    long countAll();
}
