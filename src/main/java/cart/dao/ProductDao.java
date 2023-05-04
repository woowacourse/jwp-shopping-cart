package cart.dao;

import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

    long add(Product product);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Long id);

    int updateById(Long id, Product product);

    int deleteById(Long id);
}
