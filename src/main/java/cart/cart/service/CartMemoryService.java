package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartResponse;
import cart.member.dto.MemberRequest;
import cart.member.dto.MemberResponse;
import cart.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartMemoryService implements CartService {
    private final CartDao cartDao;
    private final MemberService memberService;
    
    @Override
    @Transactional
    public Long addCart(final Long productId, final MemberRequest memberRequest) {
        return cartDao.save(productId, memberRequest.getId());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CartResponse> findByMemberRequest(final MemberRequest memberRequest) {
        final String email = memberRequest.getEmail();
        final String password = memberRequest.getPassword();
        final MemberResponse member = memberService.findByEmailAndPassword(email, password);
        
        return cartDao.findByMemberId(member.getId()).stream()
                .map(CartResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }
    
    @Override
    public void deleteByCartIdAndMemberId(final Long cartId, final Long memberId) {
        cartDao.deleteByCartIdAndMemberId(cartId, memberId);
    }
}
