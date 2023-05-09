package cart.domain.cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.cart.dto.CartDto;
import cart.domain.cart.entity.Cart;
import cart.domain.member.entity.Member;
import cart.domain.product.entity.Product;
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
    public CartDto create(final Long productId, final String memberEmail) {
        final Member member = findMember(memberEmail);
        final Product product = findProduct(productId);
        final Cart cart = new Cart(null, product, member, null, null);
        if (cartDao.exists(cart)) {
            throw new IllegalArgumentException("이미 담겨진 상품입니다.");
        }
        final Cart savedCar = cartDao.save(cart);
        return CartDto.of(savedCar);
    }

    private Member findMember(final String email) {
        return memberDao.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private Product findProduct(final Long productId) {
        return productDao.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public List<CartDto> findByEmail(final String email) {
        final Member member = memberDao.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        final List<Cart> carts = cartDao.findByMember(member);
        return carts.stream()
            .map(CartDto::of)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void delete(final Long id) {
        cartDao.deleteById(id);
    }
}
