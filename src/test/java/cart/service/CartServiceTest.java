package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.repository.MemberCartRepository;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private MemberCartRepository memberProductRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("특정 사용자의 장바구니에 물건을 추가한다.")
    void addCart() {
        // given
        when(memberProductRepository.save(any(), any())).thenReturn(1L);

        // when
        long savedCartId = cartService.addCart("journey@gmail.com", 1L);

        // then
        assertThat(savedCartId).isSameAs(1L);
    }

    @Test
    @DisplayName("특정 사용자의 장바구니에 담긴 상품 리스트를 조회한다.")
    void getProductsByMemberEmail() {
        // given
        final MemberCartEntity chickenProduct = new MemberCartEntity(1L, 1L, 1L,
            "치킨", "chicken_image_url", 20000, "KOREAN");
        final MemberCartEntity steakProductEntity = new MemberCartEntity(2L, 1L, 2L,
            "스테이크", "steakUrl", 40000, "WESTERN");
        when(memberProductRepository.findByMemberEmail(any())).thenReturn(
            List.of(chickenProduct, steakProductEntity));

        // when
        final List<ProductResponse> productResponses = cartService.getProductsByMemberEmail("journey@gmail.com");

        // then
        assertThat(productResponses).hasSize(2);
        assertThat(productResponses)
            .extracting("id", "name", "imageUrl", "price", "category")
            .containsExactly(
                tuple(1L, "치킨", "chicken_image_url", 20000, "KOREAN"),
                tuple(2L, "스테이크", "steakUrl", 40000, "WESTERN"));
    }

    @Test
    @DisplayName("장바구니 정보를 정상적으로 제거한다.")
    void deleteCart_success() {
        // given
        when(memberProductRepository.deleteByMemberEmail(any(), any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> cartService.deleteCart("journey@gmail.com", 1L));
    }

    @ParameterizedTest(name = "장바구니 정보 삭제가 실패하면 예외가 발생한다.")
    @ValueSource(ints = {0, 2})
    void deleteCart_fail(final int deletedCount) {
        // given
        when(memberProductRepository.deleteByMemberEmail(any(), any())).thenReturn(
            deletedCount);

        // when, then
        assertThatThrownBy(() -> cartService.deleteCart("journey@gmail.com", 1L))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.CART_INVALID_DELETE);
    }
}
