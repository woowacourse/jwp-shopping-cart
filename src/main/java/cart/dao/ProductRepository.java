package cart.dao;

import cart.controller.dto.ProductRequest;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "존재하지 않는 제품입니다.";

    private final CartDao mySQLCartDao;
    private final ProductDao mySQLProductDao;

    public ProductRepository(CartDao mySQLCartDao, ProductDao mySQLProductDao) {
        this.mySQLCartDao = mySQLCartDao;
        this.mySQLProductDao = mySQLProductDao;
    }

    public long add(ProductRequest product) {
        return mySQLProductDao.add(product);
    }

    public int update(Long id, ProductRequest product) {
        final int updateCount = mySQLProductDao.updateById(id, product);
        validateIfProductExist(updateCount);
        return updateCount;
    }

    public int remove(Long productId) {
        mySQLCartDao.deleteByProductId(productId);

        final int deleteCount = mySQLProductDao.deleteById(productId);
        validateIfProductExist(deleteCount);
        return deleteCount;
    }

    private static void validateIfProductExist(int changeCount) {
        if (changeCount == 0) {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE);
        }
    }
}
