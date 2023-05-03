package cart.domain.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.domain.exception.DbNotAffectedException;
import cart.domain.persistence.dao.ProductDao;
import cart.domain.persistence.entity.ProductEntity;
import cart.domain.persistence.dao.CartDao;

@Service
public class AdminService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public AdminService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public long create(final ProductEntity productEntity) {
        return productDao.save(productEntity);
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void update(final long productId, final ProductEntity original) {
        final ProductEntity entityWithId = new ProductEntity(productId, original.getName(), original.getPrice(),
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
            throw new DbNotAffectedException("변경된 정보가 없습니다.");
        }
    }
}
