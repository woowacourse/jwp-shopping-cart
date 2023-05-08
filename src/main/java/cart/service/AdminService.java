package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.exception.DbNotAffectedException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.dao.CartDao;

@Service
public class AdminService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public AdminService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public long create(final Product product) {
        return productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(final long productId, final Product original) {
        final Product entityWithId = new Product(productId, original.getName(), original.getPrice(),
            original.getImageUrl());
        int affected = productDao.update(entityWithId);
        assertRowChanged(affected);
    }

    public void delete(final long productId) {
        int affected = productDao.deleteById(productId);
        assertRowChanged(affected);
        cartDao.deleteByProductId(productId);
    }

    private void assertRowChanged(final int rowAffected) {
        if (rowAffected < 1) {
            throw new DbNotAffectedException();
        }
    }
}
