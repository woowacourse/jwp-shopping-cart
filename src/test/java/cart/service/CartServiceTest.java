package cart.service;

import cart.entity.Member;
import cart.exception.customexceptions.DataNotFoundException;
import cart.repository.dao.cartDao.CartDao;
import cart.repository.dao.memberDao.MemberDao;
import cart.repository.dao.productDao.ProductDao;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @Mock
    private MemberDao memberDao;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private CartService cartService;

    @Test
    void 존재하지_않는_사용자가_장바구니를_보려할_때_예외를_던진다() {
        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.findAllCartProductByEmail("random"))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    void 장바구니에서_찾아온_상품_ID들을_조회했을때_상품이_없으면_예외를_던진다() {
        Long id = 1L;
        String email = "ehdgur4814@naver.com";
        String name = "hardy";
        String password = "3333";
        Member member = new Member(id, email, name, password);

        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.of(member));
        when(cartDao.findAllProductIdByMemberId(any()))
                .thenReturn(List.of(1L, 2L));
        when(productDao.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.findAllCartProductByEmail(email))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");
    }

    @Test
    void 존재하지_않는_사용자가_장바구니에_상품을_추가할때_예외를_던진다() {
        Long productId = 1L;
        String email = "ehdgur4814@naver.com";

        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.addProductInCart(productId, email))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_사용자가_장바구니에_상품을_삭제할때_예외를_던진다() {
        Long productId = 1L;
        String email = "ehdgur4814@naver.com";

        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.deleteProductInCart(productId, email))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 사용자가 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_상품을_삭제할때_예외를_던진다() {
        Long productId = 1L;
        String email = "ehdgur4814@naver.com";

        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.of(new Member(1L, email, "hardy", "333")));
        when(cartDao.delete(any(), any()))
                .thenReturn(0);

        assertThatThrownBy(() -> cartService.deleteProductInCart(productId, email))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");
    }
}
