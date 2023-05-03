package cart.domain.cart.service;

import cart.domain.cart.dao.CartDao;
import cart.domain.cart.entity.Cart;
import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.domain.product.dao.ProductDao;
import cart.domain.product.entity.Product;
import cart.dto.CartCreateRequest;
import cart.dto.CartCreateResponse;
import cart.dto.CartResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao,
        final ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    @Transactional
    public CartCreateResponse create(final CartCreateRequest request) {
        final Member member = memberDao.findByEmail(request.getMemberEmail())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        final Product product = productDao.findById(request.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        final Cart cart = cartDao.save(new Cart(null, product, member, null, null));
        return CartCreateResponse.of(cart);
    }

    public List<CartResponse> findByEmail(final String email) {
        final Member member = memberDao.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        final List<Cart> carts = cartDao.findByMember(member);
        return carts.stream()
            .map(CartResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void delete(final Long id) {
        cartDao.deleteById(id);
    }
}
