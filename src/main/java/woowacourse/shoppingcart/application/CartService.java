package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, MemberDao memberDao,
                       ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int cartItemQuantity = cartItemDao.findProductQuantityIdById(cartId);
            carts.add(new Cart(cartId, product, cartItemQuantity));
        }
        return carts;
    }

    public List<Cart> findCartsByMemberId(final Long memberId) {
        final List<Long> cartIds = findCartIdsByMemberId(memberId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int cartItemQuantity = cartItemDao.findProductQuantityIdById(cartId);
            carts.add(new Cart(cartId, product, cartItemQuantity));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long memberId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByMemberId(memberId);
    }

    private List<Long> findCartIdsByMemberId(final Long memberId) {
        return cartItemDao.findIdsByMemberId(memberId);
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long memberId = customerDao.findIdByUserName(customerName);
        try {
            return cartItemDao.addCartItem(memberId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public Long addCart2(final Long productId, final Long memberId) {
        try {
            return cartItemDao.addCartItem(memberId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void updateCartItemQuantity(final Long cartId, final UpdateQuantityRequest updateQuantityRequest) {
        cartItemDao.updateCartItemQuantity(cartId, updateQuantityRequest.getQuantity());
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    public void deleteCart2(final Long memberId, final Long cartId) {
        validateMemberCart(cartId, memberId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private void validateMemberCart(final Long cartId, final Long memberId) {
        final List<Long> cartIds = findCartIdsByMemberId(memberId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
