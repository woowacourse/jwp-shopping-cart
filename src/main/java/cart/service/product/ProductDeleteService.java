package cart.service.product;

import cart.dao.ProductDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductDeleteService {
    private final ProductDao productDao;

    public ProductDeleteService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void delete(final Long id) {
        productDao.deleteById(id);
    }
}
