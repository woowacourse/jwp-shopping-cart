package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
        Optional<CartItem> optionalItem = findItemBuCustomerAndProduct(customerId, productId);
        if (optionalItem.isPresent()) {
            CartItem cartItem = optionalItem.get();
            return updateItem(cartItem, quantity);
        }
        return addItem(customerId, productId, quantity);
    }

    private Optional<CartItem> findItemBuCustomerAndProduct(Long customerId, Long productId) {
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
            .productId(cartItem.getProductId())
            .name(cartItem.getName())
            .price(cartItem.getPrice())
            .imageUrl(cartItem.getImageUrl())
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

    public void deleteItem(Long customerId, List<Long> productIds) {
        List<Long> deletedCartItemIds = findMatchWithProductId(customerId, productIds);
        validateDeleteNotExistItem(productIds, deletedCartItemIds);
        cartItemDao.deleteAll(deletedCartItemIds);
    }

    private List<Long> findMatchWithProductId(Long customerId, List<Long> productIds) {
        return cartItemDao.findByCustomerId(customerId).stream()
            .filter(item -> productIds.contains(item.getProductId()))
            .map(CartItem::getId)
            .collect(Collectors.toList());
    }

    private void validateDeleteNotExistItem(List<Long> productIds, List<Long> deletedCartItemIds) {
        if (deletedCartItemIds.size() < productIds.size()) {
            throw new NoSuchElementException("장바구니에 없는 상품을 삭제할 수 없습니다.");
        }
    }
}
