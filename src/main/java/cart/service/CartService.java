package cart.service;

import cart.entity.item.CartItem;
import cart.entity.item.CartItemDao;
import cart.entity.product.Product;
import cart.entity.product.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public CartItem addItem(final long memberId, final long productId) {
        final CartItem cartItem = new CartItem(memberId, productId);
        return cartItemDao.save(cartItem);
    }

    public List<Product> findCartItems(final long memberId) {
        final List<CartItem> cartItems = cartItemDao.findByMemberId(memberId);
        return cartItems.stream()
                .map(CartItem::getProductId)
                .map(productDao::findById)
                .collect(Collectors.toList());
    }

    public void deleteItem(final long memberId, final long productId) {
        cartItemDao.delete(memberId, productId);
    }
}
