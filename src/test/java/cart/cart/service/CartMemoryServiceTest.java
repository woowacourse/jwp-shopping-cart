package cart.cart.service;

import cart.auth.AuthSubjectArgumentResolver;
import cart.cart.dao.CartDao;
import cart.member.dto.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(CartMemoryService.class)
class CartMemoryServiceTest {
    @Autowired
    private CartService cartService;
    @MockBean
    private CartDao cartDao;
    @MockBean
    private AuthSubjectArgumentResolver resolver;
    private InOrder inOrder;
    
    @BeforeEach
    void setUp() {
        inOrder = inOrder(cartDao);
    }
    
    @Test
    void 장바구니를_추가한다() {
        // given
        final MemberRequest memberRequest = new MemberRequest(1L, "a@a.com", "password1");
        given(cartDao.save(anyLong(), anyLong())).willReturn(1L);
        
        // when
        final Long cartId = cartService.addCart(1L, memberRequest);
        
        assertAll(
                () -> assertThat(cartId).isOne(),
                () -> then(cartDao).should(only()).save(anyLong(), anyLong())
        );
    }
}
