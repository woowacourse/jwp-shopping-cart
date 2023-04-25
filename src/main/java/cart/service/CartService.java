package cart.service;

import cart.controller.dto.ProductDto;
import cart.dao.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    @Autowired
    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> findAll() {
        return productDao.selectAll();
    }
}
