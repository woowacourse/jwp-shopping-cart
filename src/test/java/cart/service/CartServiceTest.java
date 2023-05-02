package cart.service;

import cart.dao.CartDao;
import cart.dao.entity.CartEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @Test
    void 사용자_id로_장바구니_상품_id_목록을_찾는다() {
        //given
        when(cartDao.findAllByUserId(any(Long.class)))
                .thenReturn(List.of(new CartEntity(1L, 1L)));

        //when
        final List<Long> productIds = cartService.findProductIdsByUserId(1L);

        //then
        assertSoftly(softly -> {
            softly.assertThat(productIds).hasSize(1);
            softly.assertThat(productIds.get(0)).isEqualTo(1L);
        });
    }

    @Test
    void 장바구니에_상품을_넣는다() {
        //given
        when(cartDao.insert(any(CartEntity.class)))
                .thenReturn(1L);

        //when
        final Long id = cartService.insert(1L, 1L);

        //then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 장바구니_상품을_삭제한다() {
        //given
        when(cartDao.delete(any(Long.class), any(Long.class)))
                .thenReturn(1);

        //when
        final int affectedRows = cartService.delete(1L, 1L);

        //then
        assertThat(affectedRows).isEqualTo(1);
    }
}
