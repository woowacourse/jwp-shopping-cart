package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductSaveRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductSaveRequest request) {
        final Product product = new Product(request.getName(), request.getImage(), request.getPrice());

        return productDao.saveAndGetId(product)
                .orElseThrow(() -> new IllegalStateException("상품을 저장할 수 없습니다."));
    }
}
