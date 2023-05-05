package shoppingbasket.cart.service;

import shoppingbasket.cart.dao.CartDao;
import shoppingbasket.cart.dto.CartDeleteResponseDto;
import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.entity.CartProductEntity;
import shoppingbasket.member.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public CartEntity addCart(MemberEntity member, int productId) {
        return cartDao.insert(member.getId(), productId);
    }

    public List<CartSelectResponseDto> getCartByMemberID(final int memberId) {
        List<CartProductEntity> cartProducts = cartDao.selectAllCartProductByMemberId(memberId);

        return cartProducts.stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public CartDeleteResponseDto removeCart(final int cartId) {
        final int updatedRowCount = cartDao.delete(cartId);
        return new CartDeleteResponseDto(updatedRowCount);
    }
}
