package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {
    private static final String NOT_EXIST_CART = "[ERROR] 존재하지 않는 장바구니입니다.";

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartResponse findByUserName(String username) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        Cart cart = generateCart(id);
        return new CartResponse(cart);
    }

    private void validateExistName(String username) {
        if (!customerDao.isValidName(username)) {
            throw new InvalidCartItemException(NOT_EXIST_CART);
        }
    }

    private Cart generateCart(Long customerId) {
        List<CartItem> cartItems = cartItemDao.findCartItemByUserId(customerId);
        List<Integer> quantities = generateQuantities(cartItems);
        List<Boolean> checks = generateChecks(cartItems);
        List<Product> products = generateProductsInCart(cartItems);
        Map<Long, Product> cart = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            cart.put(cartItems.get(i).getId(), products.get(i));
        }
        return new Cart(cart, checks, quantities);
    }

    private List<Integer> generateQuantities(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Boolean> generateChecks(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getChecked)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Product> generateProductsInCart(List<CartItem> cartItems) {
        List<Long> productIds = cartItems.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productDao.findProducts();
        List<Product> inCartProducts = new ArrayList<>();
        for (Long productId : productIds) {
            inCartProducts.add(products.stream()
                            .filter(product -> product.isSameId(productId))
                            .findAny()
                            .orElseThrow(InvalidCartItemException::new)
            );
        }
        return inCartProducts;
    }

    public void addItem(String username, AddCartItemRequest addCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        Long productId = addCartItemRequest.getProductId();
        int quantity = addCartItemRequest.getQuantity();
        if (cartItemDao.isCartContains(id, productId)) {
            cartItemDao.increaseQuantity(id, productId, quantity);
            return;
        }
        cartItemDao.saveItemInCart(id, productId, quantity);
    }


//
//    public List<Cart> findCartsByCustomerName(final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//
//        final List<Cart> carts = new ArrayList<>();
//        for (final Long cartId : cartIds) {
//            final Long productId = cartItemDao.findProductIdById(cartId);
//            final Product product = productDao.findProductById(productId);
//            carts.add(new Cart(cartId, product));
//        }
//        return carts;
//    }
//
//    private List<Long> findCartIdsByCustomerName(final String customerName) {
//        final Long customerId = customerDao.findIdByUserName(customerName);
//        return cartItemDao.findIdsByCustomerId(customerId);
//    }
//
//    public Long addCart(final Long productId, final String customerName) {
//        final Long customerId = customerDao.findIdByUserName(customerName);
//        try {
//            return cartItemDao.addCartItem(customerId, productId);
//        } catch (Exception e) {
//            throw new InvalidProductException();
//        }
//    }
//
//    public void deleteCart(final String customerName, final Long cartId) {
//        validateCustomerCart(cartId, customerName);
//        cartItemDao.deleteCartItem(cartId);
//    }
//
//    private void validateCustomerCart(final Long cartId, final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//        if (cartIds.contains(cartId)) {
//            return;
//        }
//        throw new NotInCustomerCartItemException();
//    }
}
