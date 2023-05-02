package cart.cart.service;

import cart.member.dto.MemberRequest;

public interface CartService {
    Long addCart(final Long productId, final MemberRequest memberRequest);
}
