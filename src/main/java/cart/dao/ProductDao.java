package cart.dao;

import cart.domain.product.ProductEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    void insert(@Valid final ProductEntity productEntity);

    void updateById(final Long id, @Valid final ProductEntity productEntity);

    void deleteById(final Long id);
}
