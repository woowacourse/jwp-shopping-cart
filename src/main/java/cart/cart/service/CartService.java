package cart.cart.service;

import cart.cart.dto.CartResponse;
import cart.member.dto.MemberRequest;

import java.util.List;

public interface CartService {
    Long addCart(final Long productId, final MemberRequest memberRequest);
    
    List<CartResponse> findByMemberRequest(final MemberRequest memberRequest);
}
