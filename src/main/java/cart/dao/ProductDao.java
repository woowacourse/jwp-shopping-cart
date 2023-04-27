package cart.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<ProductEntity> findAll();

    int insert(final ProductEntity productEntity);

    Optional<ProductEntity> findById(final int id);

    void update(final ProductEntity updatedEntity);

    void delete(final int id);

    void deleteAll();

}
