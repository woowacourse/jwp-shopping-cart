package cart.respository;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductRepository {

    private static final String NO_PRODUCT_EXCEPTION_MESSAGE = "상품을 찾을 수 없습니다.";

    private final ProductDao productDao;
    private final CartDao cartDao;

    public ProductRepository(ProductDao productDao, CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public Long save(final Product product) {
        return productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void delete(final Long id) {
        final int affectedProduct = productDao.delete(id);
        validateResult(affectedProduct);

        cartDao.deleteByProductId(id);
    }

    public void update(final Long id, final Product product) {
        final int affectedRows = productDao.update(id, product);

        validateResult(affectedRows);
    }

    private static void validateResult(final int affectedRows) {
        if (affectedRows == 0) {
            throw new NoSuchElementException(NO_PRODUCT_EXCEPTION_MESSAGE);
        }
    }
}
