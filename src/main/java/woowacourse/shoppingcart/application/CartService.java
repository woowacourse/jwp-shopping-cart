package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.IdRequest;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addCart(final CartProductRequest cartProductRequest, final String customerName) {
        try {
            Long customerId = customerDao.findByUsername(customerName).getId();
            Product product = productDao.findProductById(cartProductRequest.getProductId());

            if (cartItemDao.existByCustomerIdAndProductId(customerId, cartProductRequest.getProductId())) {
                CartItem cartItem = cartItemDao.findIdByCustomerIdAndProductId(customerId, product);
                cartItemDao.updateById(cartItem.getId(), cartItem.getQuantity() + cartProductRequest.getQuantity(), cartItem.isChecked());
                return;
            }
            cartItemDao.addCartItem(customerId, cartProductRequest.getProductId(), cartProductRequest.getQuantity(), cartProductRequest.isChecked());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional(readOnly = true)
    public CartItemsResponse getCart(String customerName) {
        try {
            final List<Long> cartIds = findCartIdsByCustomerName(customerName);
            return new CartItemsResponse(cartIds.stream()
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
    public CartItemsResponse modifyCartItems(ModifyProductRequests modifyProductRequests) {
        try {
            for (ModifyProductRequest request : modifyProductRequests.getCartItems()) {
                cartItemDao.updateById(request.getId(), request.getQuantity(), request.isChecked());
            }
            return getModifyCartProducts(modifyProductRequests);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private CartItemsResponse getModifyCartProducts(ModifyProductRequests modifyProductRequests) {
        return new CartItemsResponse(modifyProductRequests
                .getCartItems()
                .stream()
                .map(item -> cartItemDao.findCartIdById(item.getId()))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteCart(DeleteProductRequest deleteProductRequest) {
        for (IdRequest idRequest : deleteProductRequest.getCartItems()) {
            cartItemDao.deleteCartItemById(idRequest.getId());
        }
    }

    @Transactional
    public void deleteAll() {
        cartItemDao.deleteAll();
    }
}
