package cart.dao;

import cart.entity.CartAddedProduct;
import cart.entity.Product;
import cart.entity.vo.Email;

import java.util.List;

public interface cartAddedProductDao {

    long insert(final Email email, final Product product);

    CartAddedProduct findById(final long id);

    List<CartAddedProduct> findProductsByUserEmail(final Email userEmail);

    void delete(final long id);
}
