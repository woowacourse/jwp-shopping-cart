package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.exception.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private MemberDao memberDao;
    @Mock
    private CartDao cartDao;
    @Mock
    private ProductService productService;

    private final MemberEntity MEMBER_ENTITY = new MemberEntity(1L, "email", "password", "name", "address", 10);
    private final AuthInfo authInfo = new AuthInfo("email", "password");

    @Test
    void 유효한_인증_정보와_상품ID로_데이터를_저장하면_예외가_발생하지_않는다() {
        Mockito.when(memberDao.findByAuthInfo(any(AuthInfo.class)))
                .thenReturn(Optional.of(MEMBER_ENTITY));

        assertThatNoException().isThrownBy(
                () -> cartService.addToCart(authInfo, 1L)
        );
    }

    @Test
    void 유효하지_않은_인증_정보와_상품ID로_데이터를_저장하면_예외가_발생한다() {
        Mockito.when(memberDao.findByAuthInfo(any(AuthInfo.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.addToCart(authInfo, 1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 인증_정보를_통해_카트에_저장된_상품들을_반환한다() {
        Mockito.when(memberDao.findByAuthInfo(any()))
                .thenReturn(Optional.of(
                        new MemberEntity(1L, "email", "password", "name", "address", 10)
                ));

        Mockito.when(cartDao.findByMemberId(anyLong()))
                .thenReturn(List.of(
                        new CartEntity(1L, 1L),
                        new CartEntity(1L, 2L)
                ));

        Mockito.when(productService.findByProductIds(any()))
                .thenReturn(List.of(
                        new ProductResponse(1L, "item1", 1000, "image1"),
                        new ProductResponse(2L, "item2", 2000, "image2")
                ));

        final var productResponses = cartService.showProductsBy(authInfo);
        assertThat(productResponses.size()).isEqualTo(2);
    }
}
