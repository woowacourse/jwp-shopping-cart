package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductResponse;
import cart.domain.product.ProductEntity;
import cart.mapper.ProductResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManagementService {

    private final ProductDao productDao;

    public ProductManagementService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findAll() {
        final List<ProductEntity> productEntities = productDao.selectAll();
        return ProductResponseMapper.from(productEntities);
    }

    public void add(final ProductEntity productEntity) {
        productDao.insert(productEntity);
    }

    public void updateById(final Long id, final ProductEntity productEntity) {
        productDao.updateById(id, productEntity);
    }

    public void deleteById(final Long id) {
        productDao.deleteById(id);
    }
}
