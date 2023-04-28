package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductDao {

    List<ProductEntity> selectAll();

    void insert(@Valid final ProductEntity productEntity);

    void update(@Valid final ProductEntity productEntity);

    void delete(@Valid final ProductEntity productEntity);
}
