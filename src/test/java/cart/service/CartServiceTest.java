package cart.service;

import cart.dao.CartDao;
import cart.domain.CartEntity;
import cart.domain.MemberEntity;
import cart.domain.ProductEntity;
import cart.dto.ResponseProductDto;
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

    private static final MemberEntity MEMBER_ENTITY = new MemberEntity(1L, "huchu@woowahan.com", "1234567a!");
    private static final ProductEntity PRODUCT_ENTITY = new ProductEntity(1L, "치킨", 10_000, "치킨 사진");

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @Test
    void 회원의_장바구니_상품_목록을_찾는다() {
        //given
        when(cartDao.findAllByMemberId(any(Long.class)))
                .thenReturn(List.of(new CartEntity(MEMBER_ENTITY, PRODUCT_ENTITY)));

        //when
        final List<ResponseProductDto> productIds = cartService.findCartProducts(MEMBER_ENTITY);

        //then
        assertSoftly(softly -> {
            softly.assertThat(productIds).hasSize(1);
            final ResponseProductDto responseProductDto = productIds.get(0);
            softly.assertThat(responseProductDto.getName()).isEqualTo(PRODUCT_ENTITY.getName());
            softly.assertThat(responseProductDto.getPrice()).isEqualTo(PRODUCT_ENTITY.getPrice());
            softly.assertThat(responseProductDto.getImage()).isEqualTo(PRODUCT_ENTITY.getImage());
        });
    }

    @Test
    void 장바구니에_상품을_넣는다() {
        //given
        when(cartDao.insert(any(CartEntity.class)))
                .thenReturn(1L);

        //when
        final Long id = cartService.insert(MEMBER_ENTITY, PRODUCT_ENTITY);

        //then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 장바구니_상품을_삭제한다() {
        //given
        when(cartDao.delete(any(Long.class), any(Long.class)))
                .thenReturn(1);

        //when
        final int affectedRows = cartService.delete(MEMBER_ENTITY, PRODUCT_ENTITY);

        //then
        assertThat(affectedRows).isEqualTo(1);
    }
}
