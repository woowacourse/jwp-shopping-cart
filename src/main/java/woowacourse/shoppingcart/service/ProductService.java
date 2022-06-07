package woowacourse.shoppingcart.service;

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
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateProductRequest;
import woowacourse.shoppingcart.dto.response.FindAllProductsResponse;

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
    public List<FindAllProductsResponse> findProducts() {
        final List<Product> products = productDao.findProducts();
        return products.stream()
                .map(FindAllProductsResponse::new)
                .collect(Collectors.toList());
    }

    public Long addProduct(final CreateProductRequest request) {
        return productDao.save(request.toProduct());
    }

    @Transactional(readOnly = true)
    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    @Transactional(readOnly = true)
    public List<FindAllProductsResponse> findProductsByCustomerName(final UserName customerName) {
        final Customer customer = customerDao.getByName(customerName);
        final Cart cart = new Cart(cartItemDao.findAllByCustomerId(customer.getId()));
        final List<Product> products = productDao.findProducts();

        return products.stream()
                .map(product -> FindAllProductsResponse.of(cart, product))
                .collect(Collectors.toList());
    }
}
