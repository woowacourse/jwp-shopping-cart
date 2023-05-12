package cart.service;

import cart.controller.exception.CartException;
import cart.dao.CartDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.dto.MemberDto;
import cart.dto.response.CartResponse;
import java.util.ArrayList;
import java.util.List;
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
    public Long insert(final Long productId, final MemberDto memberDto) {
        final Member member = memberService.find(memberDto.getEmail());
        return cartDao.insert(productId, member.getId());
    }

    @Transactional
    public int delete(final Long productId, final MemberDto memberDto) {
        final Member member = memberService.find(memberDto.getEmail());
        final int deletedRow = cartDao.delete(productId, member.getId());
        if (deletedRow == 0) {
            throw new CartException("존재하지 않는 제품입니다.");
        }
        return deletedRow;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> find(final MemberDto memberDto) {
        final Member member = memberService.find(memberDto.getEmail());
        final List<Cart> carts = cartDao.findAllByMemberId(member.getId());
        if (carts.isEmpty()) {
            return new ArrayList<>();
        }
        return carts.stream()
                .map(cart -> productService.findById(cart.getProductId()))
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
