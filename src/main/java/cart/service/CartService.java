package cart.service;

import cart.controller.dto.NewProductDto;
import cart.controller.dto.ProductDto;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
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
        List<ProductEntity> productEntities = productDao.selectAll();
        return productEntities.stream()
                .map(entity -> new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImage()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void insert(final NewProductDto newProductDto) {
        productDao.insert(newProductDto);
    }

    public void update(final ProductDto productDto) {
        productDao.update(productDto);
    }
}
