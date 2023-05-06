package shoppingbasket.cart.service;

import shoppingbasket.cart.dao.CartDao;
import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.entity.CartProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import shoppingbasket.member.entity.MemberEntity;
import shoppingbasket.member.service.MemberService;

@Service
public class  CartService {

    private final CartDao cartDao;
    private final MemberService memberService;

    public CartService(final CartDao cartDao, final MemberService memberService) {
        this.cartDao = cartDao;
        this.memberService = memberService;
    }

    public CartEntity addCart(String memberEmail, int productId) {
        final MemberEntity member = memberService.findMemberByEmail(memberEmail);
        return cartDao.insert(member.getId(), productId);
    }

    public List<CartSelectResponseDto> getCartsByMemberEmail(final String memberEmail) {
        final MemberEntity member = memberService.findMemberByEmail(memberEmail);
        List<CartProductEntity> cartProducts = cartDao.selectAllCartProductByMemberId(member.getId());

        return cartProducts.stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeCart(final int cartId) {
        cartDao.delete(cartId);
    }

    public CartSelectResponseDto getCartById(final int cartId) {
        CartProductEntity cartProduct = cartDao.selectCartById(cartId);
        return CartMapper.toDto(cartProduct);
    }
}
