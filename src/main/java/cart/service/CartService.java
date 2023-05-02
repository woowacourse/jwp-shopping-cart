package cart.service;

import cart.dao.CartMemberRepository;
import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartMemberRepository cartMemberRepository;

    public CartService(CartMemberRepository cartMemberRepository) {
        this.cartMemberRepository = cartMemberRepository;
    }

    public List<CartResponseDto> findAll(String email) {
        return cartMemberRepository.findCartByMember(email).stream().map(entity ->
                        new CartResponseDto(entity.getMemberId(), entity.getProductName(), entity.getProductImage(), entity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void save(CartRequestDto cartRequestDto) {
        cartMemberRepository.createCartByMember(cartRequestDto);
    }
}
