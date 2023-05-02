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
        if (isExistCartProduct(productId, memberRequest)) {
            throw new IllegalArgumentException("[ERROR] 해당 계정엔 해당 물품이 이미 장바구니에 존재합니다.");
        }
        
        return cartDao.save(productId, memberRequest.getId());
    }
    
    private boolean isExistCartProduct(final Long productId, final MemberRequest memberRequest) {
        return cartDao.findByMemberId(memberRequest.getId()).stream()
                .anyMatch(cart -> cart.isSame(productId, memberRequest.getId()));
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
    
    @Override
    public void deleteByProductId(final Long productId) {
        cartDao.deleteByProductId(productId);
    }
}
