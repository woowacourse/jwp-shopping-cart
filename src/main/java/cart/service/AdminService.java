package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final ProductDao productDao;

    public AdminService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void create(ProductRequest productRequest) {
        productDao.save(productRequest);
    }
}
