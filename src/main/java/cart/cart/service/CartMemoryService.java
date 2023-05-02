package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.member.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CartMemoryService implements CartService {
    private final CartDao cartDao;
    
    @Override
    public Long addCart(final Long productId, final MemberRequest memberRequest) {
        return cartDao.save(productId, memberRequest.getId());
    }
}
