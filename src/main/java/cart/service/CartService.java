package cart.service;

import cart.controller.exception.CartException;
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
        final Member member = memberService.find(memberDto);
        final Optional<Cart> cart = cartDao.find(productId, member.getId());
        if (cart.isPresent()) {
            throw new CartException("이미 장바구니에 존재하는 제품입니다.");
        }
        return cartDao.insert(productId, member.getId());
    }

    @Transactional
    public int delete(final Long productId, final MemberDto memberDto) {
        final Member member = memberService.find(memberDto);
        final Optional<Cart> cart = cartDao.find(productId, member.getId());
        if (cart.isEmpty()) {
            throw new CartException("존재하지 않는 장바구니입니다.");
        }
        return cartDao.delete(cart.get());
    }

    @Transactional(readOnly = true)
    public List<CartResponse> find(final MemberDto memberDto) {
        final Member member = memberService.find(memberDto);
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
