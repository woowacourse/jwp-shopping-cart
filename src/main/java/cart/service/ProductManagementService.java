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

    public long save(final ProductDto productDto) {
        return productDao.insert(ProductDto.toEntity(productDto));
    }

    public void update(final ProductDto productDto) {
        int updatedRowCount = productDao.update(ProductDto.toEntity(productDto));
        if (updatedRowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public void delete(final long productId) {
        int deletedRowCount = productDao.deleteById(productId);
        if (deletedRowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }
}
