package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.dto.CartItem;
import woowacourse.shoppingcart.dao.dto.EnrollCartDto;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, MemberDao memberDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Long add(Long memberId, Long productId) {
        validateExistMember(memberId);
        validateExistProduct(productId);

        EnrollCartDto enrollCartDto = new EnrollCartDto(memberId, productId);
        return cartItemDao.save(enrollCartDto);
    }

    public List<Cart> findCarts(Long memberId) {
        validateExistMember(memberId);
        List<CartItem> cartItems = cartItemDao.findCartItemsByMemberId(memberId);
        List<Cart> carts = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = productDao.findProductById(cartItem.getProduct_id())
                    .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
            carts.add(new Cart(cartItem.getCart_id(), product));
        }
        return carts;
    }

    public void deleteCart(Long memberId, Long cartId) {
        validateExistMember(memberId);
        validateExistMemberCart(cartId, memberId);
        cartItemDao.deleteById(cartId);
    }

    private void validateExistMemberCart(Long memberId, Long cartId) {
        List<CartItem> cartItems = cartItemDao.findCartItemsByMemberId(memberId);
        List<Long> cartIds = cartItems.stream()
                .map(CartItem::getCart_id)
                .collect(Collectors.toList());
        if (!cartIds.contains(cartId)) {
            throw new NotInMemberCartItemException();
        }
    }

    private void validateExistMember(Long memberId) {
        Optional<Member> member = memberDao.findMemberById(memberId);
        if (member.isEmpty()) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    private void validateExistProduct(Long productId) {
        Optional<Product> product = productDao.findProductById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
    }
}
