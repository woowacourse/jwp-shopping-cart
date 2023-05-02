package cart.cart.service;

import cart.cart.dto.CartResponse;
import cart.member.dto.MemberRequest;

import java.util.List;

public interface CartService {
    Long addCart(final Long productId, final MemberRequest memberRequest);
    
    List<CartResponse> findByMemberRequest(final MemberRequest memberRequest);
    
    void deleteByCartIdAndMemberId(final Long cartId, final Long memberId);
    
    void deleteByProductId(final Long productId);
}
