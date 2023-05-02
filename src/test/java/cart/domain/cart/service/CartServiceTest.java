package cart.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import cart.domain.cart.dao.CartDao;
import cart.domain.cart.entity.Cart;
import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.domain.product.dao.ProductDao;
import cart.domain.product.entity.Product;
import cart.dto.CartCreateRequest;
import cart.dto.CartCreateResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @DisplayName("장바구니에 담는다.")
    public void testCreate() {
        //given
        final Member member = new Member(1L, "test@test.com", "password", LocalDateTime.now(),
            LocalDateTime.now());
        final Product product = new Product(1L, "test", 1000, "imageUrl", LocalDateTime.now(),
            LocalDateTime.now());
        final CartCreateRequest request = new CartCreateRequest(1L, member.getEmail());
        given(memberDao.findByEmail(anyString()))
            .willReturn(Optional.of(member));
        given(productDao.findById(anyLong()))
            .willReturn(Optional.of(product));
        final Cart cart = new Cart(1L, product, member, LocalDateTime.now(), LocalDateTime.now());
        given(cartDao.save(any()))
            .willReturn(cart);

        //when
        final CartCreateResponse cartCreateResponse = cartService.create(request);

        //then
        assertThat(cartCreateResponse.getId()).isEqualTo(cart.getId());
        assertThat(cartCreateResponse.getMember()).isEqualTo(cart.getMember());
        assertThat(cartCreateResponse.getProduct()).isEqualTo(cart.getProduct());
    }


}
