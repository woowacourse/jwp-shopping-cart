package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Id;
import woowacourse.shoppingcart.domain.CartProducts;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.entity.CartEntity;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional
    public void addCart(final CartProductRequest cartProductRequest, final String customerName) {
        try {
            final Long customerId = customerDao.findByUsername(customerName).getId();
            if (cartItemDao.existByCustomerIdAndProductId(customerId, cartProductRequest.getProductId())) {
                CartEntity cartEntity = cartItemDao.findIdByCustomerIdAndProductId(customerId, cartProductRequest.getProductId());
                cartItemDao.updateById(cartEntity.getId(), cartEntity.getQuantity() + cartProductRequest.getQuantity(), cartEntity.isChecked());
                return;
            }
            cartItemDao.addCartItem(customerId, cartProductRequest.getProductId(), cartProductRequest.getQuantity(), cartProductRequest.isChecked());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional(readOnly = true)
    public CartProducts getCart(String customerName) {
        try {
            final List<Long> cartIds = findCartIdsByCustomerName(customerName);
            return new CartProducts(cartIds.stream()
                    .map(cartItemDao::findCartIdById)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    @Transactional
    public CartProducts modifyCartItems(ModifyProductRequests modifyProductRequests) {
        try {
            for (ModifyProductRequest request : modifyProductRequests.getCartItems()) {
                cartItemDao.updateById(request.getId(), request.getQuantity(), request.isChecked());
            }
            return getModifyCartProducts(modifyProductRequests);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private CartProducts getModifyCartProducts(ModifyProductRequests modifyProductRequests) {
        return new CartProducts(modifyProductRequests
                .getCartItems()
                .stream()
                .map(item -> cartItemDao.findCartIdById(item.getId()))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteCart(DeleteProductRequest deleteProductRequest) {
        for (Id id : deleteProductRequest.getCartItems()) {
            cartItemDao.deleteCartItemById(id.getId());
        }
    }

    @Transactional
    public void deleteAll() {
        cartItemDao.deleteAll();
    }
}
