package cart.cart_product.service;

import cart.cart.domain.Cart;
import cart.cart.service.CartService;
import cart.cart_product.dao.CartProductDao;
import cart.member.domain.Member;
import cart.member.service.MemberService;
import cart.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartProductService {

    private final MemberService memberService;
    private final CartService cartService;
    private final CartProductDao cartProductDao;

    @Transactional
    public void add(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        final Cart cart = cartService.find(member);

        if (count(cart.getId(), productId) == 1) {
            throw new IllegalArgumentException("상품이 장바구니에 이미 등록되어 있습니다");
        }
        cartProductDao.insert(cart.getId(), productId);
    }

    @Transactional(readOnly = true)
    public int count(final Long cartId, final Long productId) {
        return cartProductDao.countByCartIdAndProductId(cartId, productId);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(final String email, final String password) {
        final Member member = memberService.find(email, password);
        final Cart cart = cartService.find(member);
        return cartProductDao.findAllProductByCartId(cart.getId());
    }

    @Transactional
    public void delete(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        final Cart cart = cartService.find(member);
        cartProductDao.deleteByCartIdAndProductId(cart.getId(), productId);
    }
}
