package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public ProductService(final ProductDao productDao, final CustomerDao customerDao,
                          final CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.cartItemDao = cartItemDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        return productDao.findProducts().stream()
                .map(ProductResponse::withOutCart)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProductsByCustomerId(final Long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        final List<Product> products = productDao.findProducts();
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customer.getId());
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            final Cart cart = carts.stream()
                    .filter(each -> each.getProductId().equals(product.getId()))
                    .findFirst()
                    .orElseGet(() -> new Cart(null, 0, product));
            productResponses.add(ProductResponse.withCart(product, cart));
        }
        return productResponses;
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
