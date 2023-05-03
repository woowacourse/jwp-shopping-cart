package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    long insert(@Valid final ProductEntity productEntity);

    int updateById(@Valid final ProductEntity productEntity);

    int deleteById(@Valid final ProductEntity productEntity);
}
