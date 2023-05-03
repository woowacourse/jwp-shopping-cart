package cart.dao;

import cart.domain.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<ProductEntity> findAll();

    long insert(final ProductEntity productEntity);

    Optional<ProductEntity> findById(final int id);

    void update(final ProductEntity updatedEntity);

    void delete(int id);

}
