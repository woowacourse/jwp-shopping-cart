package cart.dao;

import cart.controller.dto.ProductRequest;
import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

    long add(ProductRequest request);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Long id);

    int updateById(Long id, ProductRequest request);

    int deleteById(Long id);
}
