package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.dto.SaveCartDto;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;
import woowacourse.shoppingcart.exception.InvalidCartQuantityException;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long add(Long memberId, Long productId) {
        validateExistMember(memberId);
        validateExistProduct(productId);
        Optional<Cart> cart = findCartWithProduct(memberId, productId);
        if (cart.isEmpty()) {
            SaveCartDto saveCartDto = new SaveCartDto(memberId, productId);
            return cartDao.save(saveCartDto);
        }

        Long cartId = cart.get().getId();
        updateQuantity(memberId, cartId, new UpdateQuantityRequest(cart.get().getQuantity() + 1));
        return cartId;
    }

    private Optional<Cart> findCartWithProduct(Long memberId, Long productId) {
        List<Cart> cartItems = cartDao.findCartByMemberId(memberId);
        return cartItems.stream()
                .filter(v -> v.getProductId().equals(productId))
                .findAny();
    }

    public List<CartResponse> findCarts(Long memberId) {
        validateExistMember(memberId);
        List<Cart> carts = cartDao.findCartByMemberId(memberId);
        return carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQuantity(Long memberId, Long cartId, UpdateQuantityRequest request) {
        validateExistMember(memberId);
        validateExistMemberCart(memberId, cartId);

        if (request.getQuantity() < 1) {
            throw new InvalidCartQuantityException();
        }
        cartDao.updateQuantity(cartId, request.getQuantity());
    }

    @Transactional
    public void deleteCart(Long memberId, Long cartId) {
        validateExistMember(memberId);
        validateExistMemberCart(memberId, cartId);
        cartDao.deleteById(cartId);
    }

    private void validateExistMemberCart(Long memberId, Long cartId) {
        List<Cart> cartItems = cartDao.findCartByMemberId(memberId);
        List<Long> cartIds = cartItems.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
        if (!cartIds.contains(cartId)) {
            throw new NotInMemberCartItemException();
        }
    }

    private void validateExistMember(Long memberId) {
        Optional<Member> member = memberDao.findMemberById(memberId);
        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }
    }

    private void validateExistProduct(Long productId) {
        Optional<Product> product = productDao.findProductById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
    }
}
