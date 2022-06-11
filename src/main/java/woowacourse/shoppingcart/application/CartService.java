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

@Service
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addCart(CartProductRequest cartProductRequest, String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        Product product = productDao.findProductById(cartProductRequest.getProductId());
        if (cartItemDao.existByCustomerIdAndProductId(customerId, cartProductRequest.getProductId())) {
            CartItem cartItem = cartItemDao.findIdByCustomerIdAndProductId(customerId, product);
            cartItemDao.updateById(cartItem.getId(), cartItem.getQuantity() + cartProductRequest.getQuantity(), cartItem.isChecked());
            return;
        }
        cartItemDao.addCartItem(customerId, cartProductRequest.getProductId(), cartProductRequest.getQuantity(), cartProductRequest.isChecked());
    }

    @Transactional(readOnly = true)
    public CartItemsResponse getCart(String customerName) {
        List<CartItem> cartItems = findCartIdsByCustomerName(customerName).stream()
                .map(cartItemDao::findCartIdById)
                .collect(Collectors.toList());

        return CartItemsResponse.from(cartItems);
    }

    private List<Long> findCartIdsByCustomerName(String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    @Transactional
    public CartItemsResponse modifyCartItems(ModifyProductRequests modifyProductRequests) {
        for (ModifyProductRequest request : modifyProductRequests.getCartItems()) {
            cartItemDao.updateById(request.getId(), request.getQuantity(), request.isChecked());
        }
        return getModifyCartProducts(modifyProductRequests);
    }

    private CartItemsResponse getModifyCartProducts(ModifyProductRequests modifyProductRequests) {
        List<CartItem> cartItems = modifyProductRequests
                .getCartItems()
                .stream()
                .map(item -> cartItemDao.findCartIdById(item.getId()))
                .collect(Collectors.toList());

        return CartItemsResponse.from(cartItems);
    }

    @Transactional
    public void deleteCart(DeleteProductRequest deleteProductRequest) {
        for (IdRequest idRequest : deleteProductRequest.getCartItems()) {
            cartItemDao.deleteCartItemById(idRequest.getId());
        }
    }

    @Transactional
    public void deleteAll(String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        cartItemDao.deleteAll(customerId);
    }
}
