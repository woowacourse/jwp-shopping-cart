package cart.service;

import static cart.service.MemberServiceTest.MEMBER_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.controller.exception.CartException;
import cart.dao.CartDao;
import cart.domain.Cart;
import cart.dto.MemberDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    public static final Cart CART_FIXTURE = new Cart(1L, 1L, 1L);
    public static final MemberDto MEMBER_DTO_FIXTURE = new MemberDto("gavi@woowahan.com", "1234");

    @Mock
    private CartDao cartDao;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        Mockito.when(memberService.find(Mockito.any()))
                .thenReturn(MEMBER_FIXTURE);
    }

    @Test
    void 장바구니를_추가할_수_있다() {
        // given
        Mockito.when(cartDao.find(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(cartDao.insert(Mockito.any(), Mockito.any()))
                .thenReturn(1);

        // when
        final int affectedRows = cartService.insert(1L, MEMBER_DTO_FIXTURE);

        // then
        assertThat(affectedRows).isOne();
    }

    @Test
    void 장바구니_물품은_중복될_수_없다() {
        // given
        Mockito.when(cartDao.find(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.ofNullable(CART_FIXTURE));

        // expect
        assertThatThrownBy(() -> cartService.insert(1L, MEMBER_DTO_FIXTURE))
                .isInstanceOf(CartException.class)
                .hasMessage("이미 장바구니에 존재하는 제품입니다.");
    }

    @Test
    void 장바구니를_삭제할_수_있다() {
        // given
        Mockito.when(cartDao.find(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.ofNullable(CART_FIXTURE));
        Mockito.when(cartDao.delete(Mockito.any()))
                .thenReturn(1);

        // when
        final int affectedRows = cartService.delete(1L, MEMBER_DTO_FIXTURE);

        // then
        assertThat(affectedRows).isOne();
    }

    @Test
    void 존재하지_않는_물품_삭제시_예외가_발생한다() {
        // given
        Mockito.when(cartDao.find(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> cartService.delete(1L, MEMBER_DTO_FIXTURE))
                .isInstanceOf(CartException.class)
                .hasMessage("존재하지 않는 장바구니입니다.");
    }
}
