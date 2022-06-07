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
        Long productId = cartItemRequest.getProductId();
        validateProduct(productId);
        return cartItemDao.save(new CartItemEntity(customerId, productId));
    }

    private void validateProduct(Long productId) {
        if (!productDao.existsById(productId)) {
            throw new InvalidProductException();
        }
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCustomerId(Long customerId) {
        validateCustomer(customerId);
        List<Long> productIds = findProductIdsByCustomerId(customerId);
        return convertToProductReponses(productDao.findByIds(productIds));
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

    private List<ProductResponse> convertToProductReponses(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(productEntity -> ProductResponse.from(productEntity.toProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(Long customerId, CartItemRequest cartItemRequest) {
        cartItemDao.delete(new CartItemEntity(customerId, cartItemRequest.getProductId()));
    }
}
