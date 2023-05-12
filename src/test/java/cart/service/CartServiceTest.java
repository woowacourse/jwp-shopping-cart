package cart.service;

import static cart.service.MemberServiceTest.MEMBER_FIXTURE;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.CartDao;
import cart.domain.Cart;
import cart.dto.MemberDto;
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

    @Test
    void 장바구니를_추가할_수_있다() {
        // given
        Mockito.when(memberService.find(Mockito.any()))
                .thenReturn(MEMBER_FIXTURE);
        Mockito.when(cartDao.insert(Mockito.any(), Mockito.any()))
                .thenReturn(1L);

        // when
        final Long insertedId = cartService.insert(2L, MEMBER_DTO_FIXTURE);

        // then
        assertThat(insertedId).isOne();
    }

    @Test
    void 장바구니_물품은_중복_가능하다() {
        // given
        Mockito.when(memberService.find(Mockito.any()))
                .thenReturn(MEMBER_FIXTURE);
        Mockito.when(cartDao.insert(Mockito.any(), Mockito.any()))
                .thenReturn(1L);
        final Long productId = 1L;

        // when
        final Long cartId = cartService.insert(productId, MEMBER_DTO_FIXTURE);

        // expect
        assertThat(cartService.insert(1L, MEMBER_DTO_FIXTURE)).isNotNull();
    }

    @Test
    void id_로_장바구니를_삭제할_수_있다() {
        // given
        Mockito.when(cartDao.delete(Mockito.any(Long.class)))
                .thenReturn(1);
        final Long id = 1L;

        // when
        final int affectedRows = cartService.delete(id);

        // then
        assertThat(affectedRows).isOne();
    }
}
