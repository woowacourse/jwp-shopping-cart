package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItem setItem(Long customerId, Long productId, int quantity) {
        Optional<CartItem> optionalItem = findByProductIdInCart(customerId, productId);
        if (optionalItem.isPresent()) {
            CartItem cartItem = optionalItem.get();
            return updateItem(cartItem, quantity);
        }
        return addItem(customerId, productId, quantity);
    }

    private Optional<CartItem> findByProductIdInCart(Long customerId, Long productId) {
        return findItemsByCustomer(customerId).stream()
            .filter(item -> item.isSameProductId(productId))
            .findAny();
    }

    private CartItem addItem(Long customerId, Long productId, int quantity) {
        Product product = productDao.findById(productId);
        CartItem cartItem = new CartItem(product, quantity);
        return cartItemDao.save(customerId, cartItem);
    }

    private CartItem updateItem(CartItem cartItem, int quantity) {
        CartItem updatedItem = CartItem.builder()
            .id(cartItem.getId())
            .product(cartItem.getProduct())
            .quantity(quantity)
            .build();
        cartItemDao.update(updatedItem);
        return updatedItem;
    }

    @Transactional(readOnly = true)
    public List<CartItem> findItemsByCustomer(Long customerId) {
        return cartItemDao.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public boolean existByCustomerAndProduct(Long customerId, Long productId) {
        return findItemsByCustomer(customerId).stream()
            .anyMatch(item -> item.isSameProductId(productId));
    }

    @Transactional(readOnly = true)
    public List<CartItem> findItemsByProductIdsInCart(Long customerId, List<Long> productIds) {
        List<CartItem> items = findItemsByCustomer(customerId).stream()
            .filter(item -> productIds.contains(item.getProductId()))
            .collect(Collectors.toList());
        validateDeleteNotExistItem(productIds, items);
        return items;
    }

    public void deleteItems(Long customerId, List<Long> productIds) {
        List<CartItem> existItems = findItemsByProductIdsInCart(customerId, productIds);
        List<Long> deletedCartItemIds = existItems.stream()
            .map(CartItem::getId)
            .collect(Collectors.toList());
        cartItemDao.deleteAll(deletedCartItemIds);
    }

    private void validateDeleteNotExistItem(List<Long> productIds, List<CartItem> items) {
        if (items.size() < productIds.size()) {
            throw new InvalidCartItemException(ErrorCode.NOT_IN_CART, "장바구니에 없는 해당 id 상품이 없습니다.");
        }
    }
}
