package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartResponse;
import cart.cart.entity.Cart;
import cart.member.entity.Member;
import cart.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberService memberService;

    public CartService(CartDao cartDao, MemberService memberService) {
        this.cartDao = cartDao;
        this.memberService = memberService;
    }

    @Transactional
    public void addCart(Long productId, String email, String password) {
        Member member = memberService.selectMemberByEmailAndPassword(email, password);
        cartDao.insertCart(productId, member.getId());
    }

    public List<CartResponse> showCart(String email, String password) {
        Member member = memberService.selectMemberByEmailAndPassword(email, password);
        List<Cart> carts = cartDao.findCartsByMemberId(member.getId());
        return carts.stream()
                .map(cart -> new CartResponse(cart.getId(), cart.getProduct().getName(), cart.getProduct().getPrice(), cart.getProduct().getImage()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCartById(Long cartId) {
        cartDao.deleteCartByCartId(cartId);
    }
}
