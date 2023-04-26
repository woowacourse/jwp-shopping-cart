package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.mapper.ProductDtoMapper;
import cart.mapper.ProductEntityMapper;
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
        return ProductDtoMapper.from(productEntities);
    }

    public void save(final ProductDto productDto) {
        productDao.insert(ProductEntityMapper.from(productDto));
    }

    public void updateById(final Long id, final ProductDto productDto) {
        productDao.updateById(id, ProductEntityMapper.from(productDto));
    }

    public void deleteById(final Long id) {
        productDao.deleteById(id);
    }
}
