package cart.service;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public long add(ProductRequest productRequest) {
        return productDao.add(productRequest);
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> productEntities = productDao.findAll();
        return ProductResponse.from(productEntities);
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        int updateCount = productDao.updateById(id, productRequest);
        validateChange(updateCount);
    }

    private static void validateChange(int changeColumnCount) {
        if (changeColumnCount == 0) {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void delete(Long id) {
        final int deleteCount = productDao.deleteById(id);
        validateChange(deleteCount);
    }
}
