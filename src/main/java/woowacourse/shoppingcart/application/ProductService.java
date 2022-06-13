package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.ProductAddRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
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
        final Customer customer = getCustomer(customerId);
        final List<Product> products = productDao.findProducts();
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customer.getId());
        return toProductResponseWithCart(products, carts);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findPageableProducts(final int limit, final int offset) {
        return productDao.findPageableProducts(limit, offset).stream()
                .map(ProductResponse::withOutCart)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findPageableProductsByCustomerId(final int size, final int page,
                                                                  final Long customerId) {
        final Customer customer = getCustomer(customerId);
        final List<Product> products = productDao.findPageableProducts(size, page);
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customer.getId());
        return toProductResponseWithCart(products, carts);
    }

    private Customer getCustomer(final Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }

    private List<ProductResponse> toProductResponseWithCart(final List<Product> products, final List<Cart> carts) {
        return products.stream()
                .map(product -> ProductResponse.withCart(product, getCartByProduct(carts, product)))
                .collect(Collectors.toList());
    }

    private Cart getCartByProduct(final List<Cart> carts, final Product product) {
        return carts.stream()
                .filter(each -> each.getProductId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> new Cart(null, new Quantity(0), product));
    }

    public Long addProduct(final ProductAddRequest productAddRequest) {
        final Product product = new Product(productAddRequest.getName(), productAddRequest.getPrice(),
                productAddRequest.getImageUrl());
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId);
        return ProductResponse.withOutCart(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
