package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManagementService {

    private final ProductDao productDao;

    public ProductManagementService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> findAll() {
        final List<ProductEntity> productEntities = productDao.selectAll();
        return ProductDto.from(productEntities);
    }

    public void save(final ProductDto productDto) {
        productDao.insert(ProductEntity.from(productDto));
    }

    public void update(final ProductDto productDto) {
        productDao.update(ProductEntity.from(productDto));
    }

    public void delete(final ProductDto productDto) {
        productDao.delete(ProductEntity.from(productDto));
    }
}
