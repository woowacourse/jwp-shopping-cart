package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductEntity> findAll() {
        return productDao.selectAll();
    }
}
