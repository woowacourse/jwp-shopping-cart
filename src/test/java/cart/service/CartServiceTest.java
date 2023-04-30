package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.MemberAuthDto;
import cart.entity.MemberEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
    @DisplayName("카트에 상품을 담을 시")
    class PunInCart {

        @Test
        @DisplayName("멤버, 상품이 유효하다면 장바구니에 추가한다.")
        void putInCart() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(productDao.findById(any())).willReturn(Optional.of(new ProductEntity(
                    1L,
                    "name",
                    "imageUrl",
                    10000,
                    "description"
            )));
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.of(new MemberEntity(
                    1L,
                    "email",
                    "password"
            )));

            final Long savedCartId = cartService.putInCart(memberAuthDto, 1L);

            assertThat(savedCartId).isNotNull();
        }

        @Test
        @DisplayName("상품이 존재하지 않으면 예외를 던진다.")
        void putInCartWithNotExistProduct() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(productDao.findById(any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.putInCart(memberAuthDto, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 상품입니다.");
        }

        @Test
        @DisplayName("멤버가 존재하지 않으면 예외를 던진다.")
        void putInCartWithNotExistMember() {
            final MemberAuthDto memberAuthDto = new MemberAuthDto(
                    "email",
                    "password"
            );
            given(productDao.findById(any())).willReturn(Optional.of(new ProductEntity(
                    1L,
                    "name",
                    "imageUrl",
                    10000,
                    "description"
            )));
            given(memberDao.findByEmailAndPassword(anyString(), anyString())).willReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.putInCart(memberAuthDto, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 회원입니다.");
        }
    }
}
