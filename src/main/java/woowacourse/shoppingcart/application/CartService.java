package woowacourse.shoppingcart.application;

import java.util.ArrayList;
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
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addCart(final Long productId, final String account) {
        final Long customerId = getCustomerIdByAccount(account);
        try {
            return cartItemDao.save(new CartItemEntity(customerId, productId));
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public List<ProductResponse> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<ProductResponse> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final CartItemEntity cartItemEntity = cartItemDao.findById(cartId);
            final ProductEntity productEntity = getProductEntity(cartItemEntity.getProductId());
            carts.add(ProductResponse.from(productEntity.toProduct()));
        }
        return carts;
    }

    private ProductEntity getProductEntity(Long productId) {
        return productDao.findById(productId)
                .orElseThrow(InvalidCustomerException::new);
    }

    private List<Long> findCartIdsByCustomerName(final String account) {
        final Long customerId = getCustomerIdByAccount(account);

        return cartItemDao.findByCustomerId(customerId)
                .stream()
                .map(CartItemEntity::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    private Long getCustomerIdByAccount(String account) {
        return customerDao.findByAccount(account)
                .orElseThrow(InvalidCustomerException::new)
                .getId();
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.delete(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
