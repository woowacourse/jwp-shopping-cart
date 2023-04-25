package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.service.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> findAllProducts() {
        List<ProductEntity> allProducts = productDao.findAll();
        return allProducts.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
