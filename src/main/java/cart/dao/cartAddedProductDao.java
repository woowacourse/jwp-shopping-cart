package cart.dao;

import cart.entity.Product;
import cart.entity.vo.Email;

import java.util.List;

public interface cartAddedProductDao {

    long insert(final Email email, final Product product);

    List<Product> findProductsByUserEmail(final Email userEmail);

    void deleteByProductId(final Email userEmail, final long productId);
}
