package cart.dao.product;

import cart.domain.product.Product;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProductDao {

    void insert(final Product product);

    Product findById(final Long id);

    List<Product> findAll();

    void updateById(final Long id, final Product product);

    void deleteById(final Long id);
}
