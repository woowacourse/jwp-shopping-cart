package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    ProductEntity selectById(final long id);

    long insert(@Valid final ProductEntity productEntity);

    int update(@Valid final ProductEntity productEntity);

    int deleteById(final long id);
}
