package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.CartItemDto;
import cart.dto.MemberDto;
import cart.dto.request.CartAddRequest;
import cart.entity.CartItemEntity;
import cart.exception.CartNotFoundException;
import cart.exception.ProductNotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@AutoConfigureMockMvc
class CartServiceTest {
    public static final CartAddRequest request = new CartAddRequest(1L);

    public static final MemberDto MEMBER = MemberDto.builder()
            .id(1L)
            .email("test@email.com")
            .password("12345678")
            .build();

    @MockBean
    private CartDao cartDao;

    @MockBean
    private MemberDao memberDao;

    @MockBean
    private ProductDao productDao;

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void add_success() {
        // given
        willReturn(true).given(productDao).existsById(anyLong());
        willReturn(1L).given(cartDao).add(anyLong(), anyLong());

        // when
        long cartId = cartService.add(request, MEMBER);

        // then
        assertThat(cartId).isEqualTo(1);
    }

    @Test
    @DisplayName("카트에 존재하지 않는 상품을 추가할 경우 예외 발생한다.")
    void addCart_ProductNotFound() {
        // given
        willReturn(false).given(productDao).existsById(anyLong());
        willReturn(1L).given(cartDao).add(anyLong(), anyLong());

        // when, then
        assertThatThrownBy(() -> cartService.add(request, MEMBER))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("존재하지 않는 product id 입니다.");
    }

    @Test
    @DisplayName("장바구니에서 상품을 제거한다.")
    void delete_success() {
        // given
        willReturn(true).given(cartDao).existsById(anyLong());
        willDoNothing().given(cartDao).deleteById(anyLong(), anyLong());

        // when, then
        assertDoesNotThrow(
                () -> cartService.delete(1, MEMBER)
        );
    }

    @Test
    @DisplayName("카트에서 삭제할 상품이 존재하지 않는 경우 예외 발생한다.")
    void delete_CartNotFound() {
        // given
        long cartId = 1L;
        willReturn(false).given(cartDao).existsById(anyLong());

        // when, then
        assertThatThrownBy(() -> cartService.delete(cartId, MEMBER))
                .isInstanceOf(CartNotFoundException.class)
                .hasMessage("존재하지 않는 cart id 입니다.");
    }

    @Test
    @DisplayName("특정 사용자의 장바구니를 조회한다.")
    void findAllByMemberId_success() {
        // given
        List<CartItemEntity> items = List.of(CartItemEntity.builder()
                .id(1)
                .price(1000)
                .name("킨더조이")
                .imageUrl("imageUrl").build());

        willReturn(true).given(productDao).existsById(anyLong());
        willReturn(items).given(cartDao).findAllByMemberId(anyLong());

        // when
        List<CartItemDto> cartItems = cartService.findAllByMemberId(MEMBER);
        CartItemDto cartItem = cartItems.get(0);

        // then
        assertAll(
                () -> assertThat(cartItems).hasSize(1),
                () -> assertThat(cartItem.getId()).isEqualTo(1),
                () -> assertThat(cartItem.getName()).isEqualTo("킨더조이"),
                () -> assertThat(cartItem.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItem.getImageUrl()).isEqualTo("imageUrl")
        );
    }
}
