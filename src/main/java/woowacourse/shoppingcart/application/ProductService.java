package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private static final int NOT_SAVED_CART_ITEM_QUANTITY = 0;

    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public ProductService(ProductDao productDao, CustomerDao customerDao,
                          CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.cartItemDao = cartItemDao;
    }

    public List<ProductResponse> findProducts(Long customerId) {
        checkExistById(customerId);
        List<Product> products = productDao.findProducts();
        return products.stream()
                .map(product -> new ProductResponse(product, findQuantityByProductIdAndCustomerId(customerId, product)))
                .collect(Collectors.toList());
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    private int findQuantityByProductIdAndCustomerId(Long customerId, Product product) {
        checkExistById(customerId);
        Optional<Integer> quantity = cartItemDao.findQuantityByProductIdAndCustomerId(
                customerId, product.getId());
        return quantity.orElse(NOT_SAVED_CART_ITEM_QUANTITY);
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
