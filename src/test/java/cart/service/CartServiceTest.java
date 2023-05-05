package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.MemberCartEntity;
import cart.dto.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartDao cartDao;

    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("사용자의 모든 카트 상품을 찾는다.")
    void findAll() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");
        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "ocean", "image", 1000);
        MemberCartEntity memberCartEntity = new MemberCartEntity(1L, 1L, "ocean", "image", 1000);

        when(memberDao.findByEmail(any())).thenReturn(new MemberEntity(1L, member.getEmail(), member.getPassword()));
        when(cartDao.findCartByMember(any())).thenReturn(List.of(memberCartEntity));

        assertThat(cartService.findAll(member)).usingRecursiveComparison().isEqualTo(List.of(productResponseDto));
    }

    @Test
    @DisplayName("사용자의 카트에 상품을 저장한다.")
    void save() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");
        doNothing().when(cartDao).save(any());
        when(memberDao.findByEmail(any())).thenReturn(new MemberEntity(1L, member.getEmail(), member.getPassword()));

        cartService.save(member, 1L);

        verify(cartDao, times(1)).save(any());
    }

    @Test
    @DisplayName("사용자의 카트에 상품을 삭제한다.")
    void delete() {
        MemberRequestDto member = new MemberRequestDto("eastsea@eastsea", "eastsea");
        doNothing().when(cartDao).save(any());
        when(memberDao.findByEmail(any())).thenReturn(new MemberEntity(1L, member.getEmail(), member.getPassword()));

        cartService.save(member, 1L);
        cartService.delete(member, 1L);

        verify(cartDao, times(1)).delete(any());
    }
}
