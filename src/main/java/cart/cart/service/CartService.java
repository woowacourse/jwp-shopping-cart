package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartInsertResponseDto;
import cart.cart.entity.CartEntity;
import cart.member.entity.MemberEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public CartInsertResponseDto addCart(MemberEntity member, int productId) {
        final CartEntity savedCart = cartDao.insert(member.getId(), productId);
        return CartMapper.toDto(savedCart);
    }
}
