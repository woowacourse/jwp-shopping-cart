package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(final CustomerDao customerDao,
                          CustomerDao customerDao1, final ProductDao productDao,
                          final CartItemDao cartItemDao) {
        this.customerDao = customerDao1;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public List<ProductResponse> findProducts(String userName) {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productDao.findProducts();
        for (Product product : products) {
            int quantity = getQuantityByUserAndProduct(userName, product);
            productResponses.add(new ProductResponse(product, quantity));
        }
        return productResponses;
    }

    private int getQuantityByUserAndProduct(String userName, Product product) {
        if (userName == null) {
            return 0;
        }

        return cartItemDao.findQuantityByUserAndProduct(
                customerDao.findIdByUserName(new UserName(userName)),
                product.getId()
        );
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
