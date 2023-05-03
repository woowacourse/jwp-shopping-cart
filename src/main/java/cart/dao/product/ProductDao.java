package cart.dao.product;

import cart.domain.product.ProductEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    ProductEntity select(final Long id);

    void insert(final ProductEntity productEntity);

    void updateById(final Long id, final ProductEntity productEntity);

    void deleteById(final Long id);
}
