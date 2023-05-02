package cart.service;

import cart.dao.CartMemberRepository;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartMemberRepository cartMemberRepository;

    public CartService(CartMemberRepository cartMemberRepository) {
        this.cartMemberRepository = cartMemberRepository;
    }

    public List<ProductResponseDto> findAll(MemberRequestDto memberRequestDto) {
        return cartMemberRepository.findCartByMember(memberRequestDto)
                .stream()
                .map(entity -> new ProductResponseDto(entity.getProductId(),
                        entity.getProductName(),
                        entity.getProductImage(),
                        entity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void save(MemberRequestDto memberRequestDto, Long id) {
        cartMemberRepository.createCartByMember(memberRequestDto, id);
    }

    @Transactional
    public void delete(MemberRequestDto memberRequestDto, Long id) {
        cartMemberRepository.delete(memberRequestDto, id);
    }
}
