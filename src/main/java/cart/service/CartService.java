package cart.service;

import cart.dao.CartDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import cart.dto.MemberDto;
import cart.dto.response.CartResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartDao cartDao;

    @Autowired
    public CartService(final MemberService memberService, final ProductService productService, final CartDao cartDao) {
        this.memberService = memberService;
        this.productService = productService;
        this.cartDao = cartDao;
    }

    @Transactional
    public int insert(final Long productId, final MemberDto memberDto) {
        final Member member = memberService.findMember(memberDto);
        final Optional<Cart> cart = cartDao.findCart(productId, member.getId());
        if (cart.isPresent()) {
            throw new IllegalArgumentException("이미 카트에 담겨진 제품입니다.");
        }
        return cartDao.insert(productId, member.getId());
    }

    @Transactional
    public int delete(final Long productId, final MemberDto memberDto) {
        final Member member = memberService.findMember(memberDto);
        final Optional<Cart> cart = cartDao.findCart(productId, member.getId());
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        return cartDao.delete(cart.get());
    }

    @Transactional(readOnly = true)
    public List<CartResponse> selectCart(final MemberDto memberDto) {
        final Member member = memberService.findMember(memberDto);
        final Long memberId = member.getId();
        final Optional<List<Cart>> cartsOptional = cartDao.findAllByMemberId(memberId);
        if (cartsOptional.isEmpty()) {
            return new ArrayList<>();
        }
        final List<Product> products = cartsOptional.get().stream()
                .map(cart -> productService.findById(cart.getProductId()))
                .collect(Collectors.toUnmodifiableList());
        return products.stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
