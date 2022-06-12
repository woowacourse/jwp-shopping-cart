package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.application.dto.AddCartServiceRequest;
import woowacourse.shoppingcart.application.dto.UpdateQuantityServiceRequest;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.dto.SaveCartDto;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartQuantityException;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;
import woowacourse.shoppingcart.ui.dto.CartResponse;

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
    public long add(AddCartServiceRequest request) {
        validateExistMember(request.getMemberId());
        validateExistProduct(request.getProductId());
        Optional<Cart> cart = cartDao.findCartByMemberIdAndProductId(request.getMemberId(), request.getProductId());
        if (cart.isEmpty()) {
            SaveCartDto saveCartDto = new SaveCartDto(request.getMemberId(), request.getProductId());
            return cartDao.save(saveCartDto);
        }

        long cartId = cart.get().getId();
        updateQuantity(new UpdateQuantityServiceRequest(request.getMemberId(), cartId, cart.get().getQuantity() + 1));
        return cartId;
    }

    public List<CartResponse> findCarts(long memberId) {
        validateExistMember(memberId);
        List<Cart> carts = cartDao.findCartByMemberId(memberId);
        return carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQuantity(UpdateQuantityServiceRequest request) {
        validateExistMember(request.getMemberId());
        validateExistMemberCart(request.getMemberId(), request.getCartId());

        if (request.getQuantity() < 1) {
            throw new InvalidCartQuantityException();
        }
        cartDao.updateQuantity(request.getCartId(), request.getQuantity());
    }

    @Transactional
    public void deleteCart(long memberId, long cartId) {
        validateExistMember(memberId);
        validateExistMemberCart(memberId, cartId);
        cartDao.deleteById(cartId);
    }

    private void validateExistMemberCart(long memberId, long cartId) {
        List<Cart> cartItems = cartDao.findCartByMemberId(memberId);
        List<Long> cartIds = cartItems.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
        if (!cartIds.contains(cartId)) {
            throw new NotInMemberCartItemException();
        }
    }

    private void validateExistMember(long memberId) {
        Optional<Member> member = memberDao.findMemberById(memberId);
        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }
    }

    private void validateExistProduct(long productId) {
        Optional<Product> product = productDao.findProductById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
    }
}
