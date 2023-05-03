package cart.dao.product;

import cart.domain.product.ProductEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProductDao {

    void insert(final ProductEntity productEntity);

    ProductEntity findById(final Long id);

    List<ProductEntity> findAll();

    void updateById(final Long id, final ProductEntity productEntity);

    void deleteById(final Long id);
}
