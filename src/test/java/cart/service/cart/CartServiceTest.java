package cart.service.cart;

import cart.service.member.Member;
import cart.service.member.MemberDao;
import cart.service.product.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    MemberDao memberDao;

    @Mock
    ProductDao productDao;

    @Mock
    CartDao cartDao;

    @InjectMocks
    CartService cartService;

    @Test
    void 장바구니에_상품을_추가한다() {
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.of(new Member(1L, "Cyh6099@gmail.com", "qwer1234")));

        given(productDao.findById(any()))
                .willReturn(Optional.of(new Product(1L, new ProductName("치킨"), new ProductImage("image"), new ProductPrice(10000))));

        given(cartDao.addProduct(any()))
                .willReturn(1L);

        Long cartId = cartService.createCartItem("cyh6099@gmail.com", 1L);

        Assertions.assertThat(cartId).isPositive();
    }

    @Test
    void 존재하지_않는_멤버로_저장하려고_하면_예외발생() {
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.createCartItem("cyh6099@gmail.com", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    void 존재하지_않는_상품을_저장하려고_하면_예외발생() {
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.of(new Member(1L, "aa@aa.com", "qwer1234")));

        given(productDao.findById(any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.createCartItem("cyh6099@gmail.com", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    void 존재하지_않는_상품을_삭제하려고_하면_예외발생() {
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.of(new Member(1L, "aa@aa.com", "qwer1234")));


        assertThatThrownBy(() -> cartService.deleteCartItem("cyh6099@gmail.com", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품은 삭제할 수 없습니다.");
    }
}
