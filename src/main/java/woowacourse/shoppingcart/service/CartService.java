package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.*;
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
        Quantities quantities = generateQuantities(cartItems);
        List<Boolean> checks = generateChecks(cartItems);
        List<Product> products = generateProductsInCart(cartItems);
        Map<Long, Product> cart = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            cart.put(cartItems.get(i).getId(), products.get(i));
        }
        return new Cart(cart, checks, quantities);
    }

    private Quantities generateQuantities(List<CartItem> cartItems) {
        return new Quantities(cartItems.stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.toUnmodifiableList()));
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
        Id productId = Id.from(addCartItemRequest.getProductId(), "상품");
        validateExistProductId(productId.getId());
        Quantity quantity = new Quantity(addCartItemRequest.getQuantity());
        if (cartItemDao.isCartContains(id, productId.getId())) {
            cartItemDao.increaseQuantity(id, productId.getId(), quantity.getQuantity());
            return;
        }
        cartItemDao.saveItemInCart(id, productId.getId(), quantity.getQuantity());
    }

    private void validateExistProductId(Long productId) {
        if (!productDao.isValidId(productId)) {
            throw new InvalidCartItemException("[ERROR] 존재하는 상품이 아닙니다.");
        }
    }

    public CartResponse updateItem(String username, UpdateCartItemRequest updateCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        Ids cartIds = new Ids(updateCartItemRequest.generateCartIds(), "항목");
        validateExistCartIds(cartIds, id);
        Quantities quantities = new Quantities(updateCartItemRequest.generateQuantities());
        List<Boolean> checked = updateCartItemRequest.generateChecked();
        for (int i = 0; i < quantities.getSize(); i++) {
            cartItemDao.updateQuantityAndCheck(id, cartIds.getIdAt(i), quantities.getQuantityAt(i), checked.get(i));
        }
        Cart cart = generateCart(id);
        return new CartResponse(generateUpdatedCart(cart, cartIds, quantities, checked));
    }

    private void validateExistCartIds(Ids cartIds, Long customerId) {
        List<Long> totalIds = cartItemDao.findCartItemByUserId(customerId).stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        if (cartIds.isNotContained(totalIds)) {
            throw new InvalidCartItemException(NOT_EXIST_CART_ITEM);
        }
    }

    private Cart generateUpdatedCart(Cart cart, Ids cartIds, Quantities quantities, List<Boolean> checked) {
        Map<Long, Product> updatedCart = new HashMap<>();
        for (int i = 0; i < cartIds.getSize(); i++) {
            Long cartId = cartIds.getIdAt(i);
            updatedCart.put(cartId, cart.pickOneProduct(cartId));
        }
        return new Cart(updatedCart, checked, quantities);
    }

    public void deleteItem(String username, DeleteCartItemRequest deleteCartItemRequest) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        Ids cartIds = new Ids(deleteCartItemRequest.generateIds(), "항목");
        validateExistCartIds(cartIds, id);
        for (int i = 0; i < cartIds.getSize(); i++) {
            cartItemDao.deleteOneItem(id, cartIds.getIdAt(i));
        }
    }

    public void deleteCart(String username) {
        validateExistName(username);
        Long id = customerDao.findIdByUserName(username);
        cartItemDao.deleteCart(id);
    }
}
