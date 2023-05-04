package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.MemberAuthDto;
import cart.dto.response.CartProductResponseDto;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartDao cartDao;
    @Mock
    private MemberDao memberDao;
    @Mock
    private ProductDao productDao;

    @Nested
    @DisplayName("회원 장바구니 상품 목록 조회 시")
    class FindCartItemsForMember {

        @Test
        @DisplayName("멤버가 유효하다면 장바구니 상품 목록을 조회한다.")
        void findCartItemsForMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            final CartEntity cartEntity = new CartEntity(1L, 1L, 1L);
            final ProductEntity productEntity = new ProductEntity(
                    1L,
                    "name",
                    "imageUrl",
                    1000,
                    "description"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(cartDao.findAllByMemberId(any())).willReturn(List.of(cartEntity));
            given(productDao.findById(any())).willReturn(Optional.of(productEntity));

            final List<CartProductResponseDto> result = cartService.findCartItemsForMember(memberAuthDto);

            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(result.get(0).getProductId()).isEqualTo(1),
                    () -> assertThat(result.get(0).getName()).isEqualTo("name"),
                    () -> assertThat(result.get(0).getImageUrl()).isEqualTo("imageUrl"),
                    () -> assertThat(result.get(0).getPrice()).isEqualTo(1000),
                    () -> assertThat(result.get(0).getDescription()).isEqualTo("description")
            );
        }

        @Test
        @DisplayName("멤버가 유효하지 않으면 예외를 던진다.")
        void findCartItemsForInvalidMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.findCartItemsForMember(memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 회원입니다.");
        }
    }

    @Nested
    @DisplayName("카트에 상품을 담을 시")
    class PunInCart {

        @Test
        @DisplayName("멤버, 상품이 유효하다면 장바구니에 추가한다.")
        void putInCart() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(productDao.findById(any())).willReturn(Optional.of(new ProductEntity(
                    1L,
                    "name",
                    "imageUrl",
                    10000,
                    "description"
            )));

            final Long result = cartService.putInCart(1L, memberAuthDto);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("상품이 존재하지 않으면 예외를 던진다.")
        void putInCartWithNotExistProduct() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(productDao.findById(any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.putInCart(1L, memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 상품입니다.");
        }

        @Test
        @DisplayName("멤버가 존재하지 않으면 예외를 던진다.")
        void putInCartWithNotExistMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.putInCart(1L, memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 회원입니다.");
        }
    }

    @Nested
    @DisplayName("카트에서 상품을 제거할 시")
    class RemoveCartItem {

        @Test
        @DisplayName("카트, 멤버가 유효하다면 상품을 제거한다.")
        void removeCartItem() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(cartDao.findById(any())).willReturn(Optional.of(new CartEntity(1L, 1L, 1L)));
            willDoNothing().given(cartDao).delete(1L);

            assertThatNoException()
                    .isThrownBy(() -> cartService.removeCartItem(1L, memberAuthDto));
        }

        @Test
        @DisplayName("멤버가 유효하지 않으면 예외를 던진다.")
        void removeCartItemWithInvalidMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.removeCartItem(1L, memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 회원입니다.");
        }

        @Test
        @DisplayName("카트 상품이 유효하지 않으면 예외를 던진다.")
        void removeCartItemWithInvalidCart() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(cartDao.findById(any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.removeCartItem(1L, memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 장바구니 상품입니다.");
        }

        @Test
        @DisplayName("카트 상품 소유주가 아니라면 예외를 던진다.")
        void removeCartItemWithNotOwner() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));
            given(cartDao.findById(any())).willReturn(Optional.of(new CartEntity(1L, 2L, 1L)));

            assertThatThrownBy(() -> cartService.removeCartItem(1L, memberAuthDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("장바구니 상품 소유자가 아닙니다.");
        }
    }
}
