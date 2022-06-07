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
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long create(Long productId, String account) {
        Long customerId = getCustomerIdByAccount(account);
        return cartItemDao.save(new CartItemEntity(customerId, productId));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByAccount(final String account) {
        Long customerId = getCustomerIdByAccount(account);
        List<Long> productIds = findProductIdsByCustomerId(customerId);

        return convertToProductReponses(productDao.findByIds(productIds));
    }

    private Long getCustomerIdByAccount(String account) {
        return customerDao.findByAccount(account)
                .orElseThrow(InvalidCustomerException::new)
                .getId();
    }

    private List<Long> findProductIdsByCustomerId(Long customerId) {
        return cartItemDao.findByCustomerId(customerId)
                .stream()
                .map(CartItemEntity::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<ProductResponse> convertToProductReponses(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(productEntity -> ProductResponse.from(productEntity.toProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteCart(Long cartId) {
        cartItemDao.delete(cartId);
    }
}
