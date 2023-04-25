package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int save(ProductRequest productRequest) {
        return productDao.save(productRequest);
    }
}
