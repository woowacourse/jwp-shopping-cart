package cart.cart.service;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartInsertResponseDto;
import cart.cart.dto.CartSelectResponseDto;
import cart.cart.entity.CartEntity;
import cart.cart.entity.CartProductEntity;
import cart.member.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<CartSelectResponseDto> getCartByMemberID(final int memberId) {
        List<CartProductEntity> cartProducts = cartDao.selectAllCartProductByMemberId(memberId);

        return cartProducts.stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
