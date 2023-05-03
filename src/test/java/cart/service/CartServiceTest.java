package cart.service;

import cart.dao.CartMemberRepository;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.MemberCartEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartMemberRepository cartMemberRepository;

    @Test
    @DisplayName("사용자의 모든 카트 상품을 찾는다.")
    void findAll() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");
        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "ocean", "image", 1000);
        MemberCartEntity memberCartEntity = new MemberCartEntity(1L, 1L, "ocean", "image", 1000);

        when(cartMemberRepository.findCartByMember(any())).thenReturn(List.of(memberCartEntity));

        Assertions.assertThat(cartService.findAll(member)).usingRecursiveComparison().isEqualTo(List.of(productResponseDto));
    }

    @Test
    @DisplayName("사용자의 카트에 상품을 저장한다.")
    void save() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");

        cartService.save(member, 1L);

        verify(cartMemberRepository, times(1)).createCartByMember(member, 1L);
    }

    @Test
    @DisplayName("사용자의 카트에 상품을 삭제한다.")
    void delete() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");
        cartService.save(member, 1L);

        cartService.delete(member, 1L);

        verify(cartMemberRepository, times(1)).delete(member, 1L);
    }
}
