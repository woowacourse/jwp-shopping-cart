package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.dao.entity.ProductEntity;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.ui.dto.CartItemRequest;

@Service
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    public CartService(CartItemDao cartItemDao, ProductDao productDao, CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
    }

    public Long create(CartItemRequest cartItemRequest, Long customerId) {
        Product product = findProductById(cartItemRequest.getProductId());
        Cart cart = new Cart(null, customerId, product);
        return cartItemDao.save(CartItemEntity.from(cart));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCustomerId(Long customerId) {
        validateCustomer(customerId);
        List<Long> productIds = findProductIdsByCustomerId(customerId);
        return convertToProductResponses(productDao.findByIds(productIds));
    }

    private void validateCustomer(Long customerId) {
        if (!customerDao.existsById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    private List<Long> findProductIdsByCustomerId(Long customerId) {
        return cartItemDao.findByCustomerId(customerId)
                .stream()
                .map(CartItemEntity::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<ProductResponse> convertToProductResponses(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(productEntity -> ProductResponse.from(productEntity.toProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(Long customerId, CartItemRequest cartItemRequest) {
        Product product = findProductById(cartItemRequest.getProductId());
        Cart cart = new Cart(null, customerId, product);
        cartItemDao.delete(CartItemEntity.from(cart));
    }

    private Product findProductById(Long productId) {
        ProductEntity productEntity = productDao.findById(productId)
                .orElseThrow(InvalidProductException::new);

        return productEntity.toProduct();
    }
}
