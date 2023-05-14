package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.cart.domain.Cart;
import cart.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;

    @Transactional(readOnly = true)
    public Cart find(final Member member) {
        return cartDao.findById(member.getId());
    }
}
