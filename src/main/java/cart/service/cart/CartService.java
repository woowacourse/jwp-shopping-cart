package cart.service.cart;

import cart.authentication.MemberInfo;
import cart.dao.cart.CartDao;
import cart.dao.member.MemberDao;
import cart.dao.product.ProductDao;
import cart.dto.cartitem.CartItem;
import cart.dto.cartitem.CartItemRequest;
import cart.dto.cartitem.CartItemResponse;
import cart.entity.cart.Cart;
import cart.entity.cart.Count;
import cart.entity.member.Member;
import cart.entity.product.Product;
import cart.exception.notfound.MemberNotFoundException;
import cart.exception.notfound.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long saveCart(final MemberInfo memberInfo, final CartItemRequest cartItemRequest) {
        Member member = memberDao.findByEmailAndPassword(memberInfo.getEmail(), memberInfo.getPassword())
            .orElseThrow(MemberNotFoundException::new);
        Product product = productDao.findById(cartItemRequest.getProductId())
            .orElseThrow(ProductNotFoundException::new);

        Optional<Cart> cart = cartDao.findByMemberIdAndProductId(member, product.getId());

        if (cart.isEmpty()) {
            return cartDao.insertCart(new Cart(member.getId(), product.getId(), 1));
        }

        return update(cart.get(), 1);
    }

    @Transactional
    public List<CartItemResponse> findCartByMember(final MemberInfo memberInfo) {
        Member member = memberDao.findByEmailAndPassword(memberInfo.getEmail(), memberInfo.getPassword())
            .orElseThrow(MemberNotFoundException::new);

        List<CartItem> cartItems = cartDao.findByMemberId(member);

        return cartItems.stream()
            .map(CartItemResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void deleteCartItem(final MemberInfo memberInfo, final Long cartItemId) {
        Member member = memberDao.findByEmailAndPassword(memberInfo.getEmail(), memberInfo.getPassword())
            .orElseThrow(MemberNotFoundException::new);
        Cart cart = cartDao.findByMemberIdAndProductId(member, cartItemId)
            .orElseThrow(ProductNotFoundException::new);

        if (cart.getCount() > 1) {
            update(cart, -1);
            return;
        }
        cartDao.deleteCart(member, cartItemId);
    }

    private Long update(final Cart cart, final int count) {
        Cart updateCart = new Cart(
            cart.getId(),
            cart.getMemberId(),
            cart.getProductId(),
            new Count(cart.getCount() + count));
        cartDao.updateCart(updateCart);
        return updateCart.getId();
    }
}
