package cart.service;

import cart.dao.JdbcCartProductDao;
import cart.dao.dto.CartProductResultMap;
import cart.dto.CartResponse;
import cart.dto.CartResponses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartMockServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private JdbcCartProductDao jdbcCartProductDao;

    @Test
    void 사용자의_id가_주어지면_해당_사용자의_장바구니_목록을_전체_조회한다() {
        // given
        final Long userId = 1L;
        final CartProductResultMap result = new CartProductResultMap(1L, 1L, "치킨", 10000, "imgUrl");
        when(jdbcCartProductDao.findAllByUserId(userId)).thenReturn(List.of(result));

        // when
        final CartResponses cartResponses = cartService.findAllByUserId(userId);

        // then
        final List<CartResponse> results = cartResponses.getCartResponses();
        assertAll(
                () -> assertThat(results).hasSize(1),
                () -> assertThat(results.get(0).getProductId()).isEqualTo(1L)
        );
    }
}
