package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.AuthInfo;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@MockitoSettings
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private MemberDao memberDao;
    @MockBean
    private CartDao cartDao;
    @MockBean
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

        assertThatIllegalArgumentException().isThrownBy(
                () -> cartService.addToCart(authInfo, 1L)
        ).withMessage("존재하지 않는 회원입니다.");
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
                        new ProductEntity(1L, "item1", 1000, "image1"),
                        new ProductEntity(2L, "item2", 2000, "image2")
                ));

        final List<ProductEntity> productResponses = cartService.showProductsBy(authInfo);
        assertThat(productResponses.size()).isEqualTo(2);
    }
}
