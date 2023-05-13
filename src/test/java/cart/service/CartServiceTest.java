package cart.service;

import cart.controller.authentication.AuthInfo;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.*;
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

    private static final AuthInfo AUTH_INFO = new AuthInfo("huchu@woowahan.com", "1234567a!");
    private static final Long PRODUCT_ID = 1L;
    private static final MemberEntity MEMBER_ENTITY = new MemberEntity(1L, new Email("huchu@woowahan.com"), new Password("1234567a!"));
    private static final ProductEntity PRODUCT_ENTITY = new ProductEntity(PRODUCT_ID, "치킨", 10_000, "치킨 사진");

    @Mock
    private CartDao cartDao;
    @Mock
    private MemberDao memberDao;
    @Mock
    private ProductDao productDao;

    @InjectMocks
    private CartService cartService;

    @Test
    void 회원의_장바구니_상품_목록을_찾는다() {
        //given
        when(memberDao.findByEmail(any(String.class)))
                .thenReturn(MEMBER_ENTITY);

        when(cartDao.findAllByMemberId(any(Long.class)))
                .thenReturn(List.of(new CartEntity(MEMBER_ENTITY, PRODUCT_ENTITY)));

        //when
        final List<ResponseProductDto> productIds = cartService.findCartProducts(AUTH_INFO);

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
        final Long id = cartService.insert(AUTH_INFO, PRODUCT_ID);

        //then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 장바구니_상품을_삭제한다() {
        //given
        when(memberDao.findByEmail(any(String.class)))
                .thenReturn(MEMBER_ENTITY);

        when(productDao.findById(any(Long.class)))
                .thenReturn(PRODUCT_ENTITY);

        when(cartDao.delete(any(Long.class), any(Long.class)))
                .thenReturn(1);

        //when
        final int affectedRows = cartService.delete(AUTH_INFO, PRODUCT_ID);

        //then
        assertThat(affectedRows).isEqualTo(1);
    }
}
