package cart.domain.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.domain.DbNotAffectedException;
import cart.domain.admin.persistence.dao.ProductDao;
import cart.domain.admin.persistence.entity.ProductEntity;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public long create(final ProductEntity productEntity) {
        return productDao.save(productEntity);
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void update(final long id, final ProductEntity original) {
        final ProductEntity entityWithId = new ProductEntity(id, original.getName(), original.getPrice(),
            original.getImageUrl());
        int affected = productDao.update(entityWithId);
        assertRowChanged(affected);
    }

    public void delete(final long id) {
        int affected = productDao.deleteById(id);
        assertRowChanged(affected);
    }

    private void assertRowChanged(final int rowAffected) {
        if (rowAffected < 1) {
            throw new DbNotAffectedException("변경된 정보가 없습니다.");
        }
    }
}
