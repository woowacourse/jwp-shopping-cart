package cart.dao.product;

import cart.domain.product.ProductEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    void insert(final ProductEntity productEntity);

    void updateById(final Long id, final ProductEntity productEntity);

    void deleteById(final Long id);
}
