package cart.service;

import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private MemberDao memberDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @DisplayName("사용자 이메일과 productId를 cart에 정상적으로 저장한다.")
    @Test
    void save_success() {
        // given
        given(memberDao.findIdByEmail(any())).willReturn(1L);
        given(cartDao.existsByMemberIdAndProductId(any(), any())).willReturn(Optional.empty());

        // when, then
        assertDoesNotThrow(() -> cartService.save("a@a.com", 1L));
    }

    @DisplayName("이미 담겨있는 상품일 경우 IllegalArgumentException 예외를 반환한다.")
    @Test
    void save_fail() {
        // given
        given(memberDao.findIdByEmail(any())).willReturn(1L);
        given(cartDao.existsByMemberIdAndProductId(any(), any())).willReturn(Optional.of(1L));

        // when, then
        assertThatThrownBy(() -> cartService.save("a@a.com", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장바구니에 이미 담겨있는 상품입니다.");
    }

    @DisplayName("사용자 이메일을 이용하여 장바구니에 담긴 상품들을 조회하여 ProductResponse의 List 타입으로 반환한다.")
    @Test
    void findByEmail() {
        // given
        Product product1 = Product.from(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        Product product2 = Product.from(2L, "피자", "https://cdn.dominos.co.kr/admin/upload/goods/20210226_GYHC7RpD.jpg", 20000);

        given(cartDao.findByEmail(any())).willReturn(List.of(product1, product2));

        // when
        List<ProductResponse> productResponses = cartService.findByEmail("a@a.com");

        // then
        assertSoftly(softly -> {
            softly.assertThat(productResponses.size()).isEqualTo(2);
            softly.assertThat(productResponses.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(product1);
        });
    }

    @DisplayName("사용자 이메일과 productId를 통해 장바구니에 담긴 상품을 정상적으로 삭제한다.")
    @Test
    void delete() {
        // given
        given(memberDao.findIdByEmail(any())).willReturn(1L);
        willDoNothing().given(cartDao).deleteByMemberIdAndProductId(any(), any());

        // when, then
        assertDoesNotThrow(() -> cartService.delete("a@a.com", 1L));
    }
}
