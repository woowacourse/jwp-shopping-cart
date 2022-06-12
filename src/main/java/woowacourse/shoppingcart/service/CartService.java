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
@Transactional
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
                            .orElseThrow(() -> new InvalidCartItemException(NOT_EXIST_CART_ITEM))
            );
        }
        return inCartProducts;
    }

    public void addItem(String username, AddCartItemRequest addCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        Long productId = addCartItemRequest.getProductId();
        validatePositiveProductId(productId);
        validateExistProductId(productId);
        int quantity = addCartItemRequest.getQuantity();
        validatePositiveQuantity(quantity);
        if (cartItemDao.isCartContains(id, productId)) {
            cartItemDao.increaseQuantity(id, productId, quantity);
            return;
        }
        cartItemDao.saveItemInCart(id, productId, quantity);
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidCartItemException("[ERROR] 상품 수는 자연수여야 합니다.");
        }
    }

    private void validateExistProductId(Long productId) {
        if (!productDao.isValidId(productId)) {
            throw new InvalidCartItemException("[ERROR] 존재하는 상품이 아닙니다.");
        }
    }

    private void validatePositiveProductId(Long productId) {
        if (productId <= 0) {
            throw new InvalidCartItemException("[ERROR] 상품 ID는 자연수여야 합니다.");
        }
    }

    public CartResponse updateItem(String username, UpdateCartItemRequest updateCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        List<Long> cartIds = updateCartItemRequest.generateCartIds();
        validatePositiveCartIds(cartIds);
        validateExistCartIds(cartIds, id);
        List<Integer> quantities = updateCartItemRequest.generateQuantities();
        validatePositiveQuantities(quantities);
        List<Boolean> checked = updateCartItemRequest.generateChecked();
        for (int i = 0; i < quantities.size(); i++) {
            cartItemDao.updateQuantityAndCheck(id, cartIds.get(i), quantities.get(i), checked.get(i));
        }
        Cart cart = generateCart(id);
        return new CartResponse(generateUpdatedCart(cart, cartIds, quantities, checked));
    }

    private void validatePositiveQuantities(List<Integer> quantities) {
        if (quantities.stream().anyMatch(quantity -> quantity <= 0)) {
            throw new InvalidCartItemException("[ERROR] 상품 수는 자연수여야 합니다.");
        }
    }

    private void validateExistCartIds(List<Long> cartIds, Long customerId) {
        List<Long> totalIds = cartItemDao.findCartItemByUserId(customerId).stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        if (cartIds.stream().anyMatch(cartId -> !totalIds.contains(cartId))) {
            throw new InvalidCartItemException(NOT_EXIST_CART_ITEM);
        }
    }

    private void validatePositiveCartIds(List<Long> cartIds) {
        if (cartIds.stream().anyMatch(cartId -> cartId <= 0)) {
            throw new InvalidCartItemException("[ERROR] 항목 ID는 자연수여야 합니다.");
        }
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
        List<Long> cartIds = deleteCartItemRequest.generateIds();
        validatePositiveCartIds(cartIds);
        validateExistCartIds(cartIds, id);
        for (Long cartId : cartIds) {
            cartItemDao.deleteOneItem(id, cartId);
        }
    }

    public void deleteCart(String username) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        cartItemDao.deleteCart(id);
    }
}
