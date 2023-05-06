package cart.service;

import cart.dao.product.ProductDao;
import cart.domain.product.Product;
import cart.dto.ProductResponse;
import cart.mapper.ProductResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(final Product product) {
        productDao.insert(product);
    }

    public List<ProductResponse> findAll() {
        final List<Product> productEntities = productDao.findAll();
        return ProductResponseMapper.from(productEntities);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByIds(final List<Long> productIds) {
        final List<Product> productEntities = new ArrayList<>();
        for (Long productId : productIds) {
            productEntities.add(productDao.findById(productId));
        }

        return ProductResponseMapper.from(productEntities);
    }

    public void updateById(final Long id, final Product product) {
        productDao.updateById(id, product);
    }

    public void deleteById(final Long id) {
        productDao.deleteById(id);
    }
}
