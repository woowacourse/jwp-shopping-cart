package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {
    private static final String NOT_EXIST_CART = "[ERROR] 존재하지 않는 장바구니입니다.";
    public static final String NOT_EXIST_CART_ITEM = "[ERROR] 장바구니에 없는 상품이 있습니다.";

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

    public CartResponse updateItem(String username, UpdateCartItemRequest updateCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        List<Long> cartIds = updateCartItemRequest.getCartIds();
        List<Integer> quantities = updateCartItemRequest.getQuantities();
        List<Boolean> checked = updateCartItemRequest.getChecked();
        for (int i = 0; i < quantities.size(); i++) {
            cartItemDao.updateQuantityAndCheck(id, cartIds.get(i), quantities.get(i), checked.get(i));
        }
        Cart cart = generateCart(id);
        return new CartResponse(generateUpdatedCart(cart, cartIds, quantities, checked));
    }

    private Cart generateUpdatedCart(Cart cart, List<Long> cartIds, List<Integer> quantities, List<Boolean> checked) {
        Map<Long, Product> updatedCart = new HashMap<>();
        Collections.sort(cartIds);
        for (Long cartId : cartIds) {
            updatedCart.put(cartId, cart.pickOneProduct(cartId));
        }
        return new Cart(updatedCart, checked, quantities);
    }

    public void deleteItem(String username, DeleteCartItemRequest deleteCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        List<Long> cartIds = deleteCartItemRequest.getIds();
        for (Long cartId : cartIds) {
            cartItemDao.deleteOneItem(id, cartId);
        }
    }
}
