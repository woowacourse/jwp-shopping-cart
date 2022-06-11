package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.물품추가;
import static woowacourse.Fixtures.사용자추가;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.카트추가;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.헌치;
import static woowacourse.Fixtures.헌치_치킨;
import static woowacourse.Fixtures.헌치_치킨_2;
import static woowacourse.Fixtures.헌치_피자;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @DisplayName("해당 아이디의 회원의 장바구니를 반환한다.")
    @Test
    void findCartsByCustomerId() {
        //given
        Long 사용자아이디 = 사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);
        물품추가(jdbcTemplate, 피자);
        카트추가(jdbcTemplate, 헌치_치킨);
        카트추가(jdbcTemplate, 헌치_피자);

        // when
        List<CartResponse> carts = cartService.findCartsByCustomerId(사용자아이디);

        // then
        assertThat(carts)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(
                        CartResponse.of(new Cart(헌치_치킨.getId(), 헌치_치킨.getQuantity(), 치킨)),
                        CartResponse.of(new Cart(헌치_피자.getId(), 헌치_피자.getQuantity(), 피자))
                ));
    }

    @DisplayName("해당 회원의 장바구니에 물품을 추가한다.")
    @Test
    void addCart() {
        //given
        Long 사용자아이디 = 사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);

        //when
        cartService.addCarts(List.of(new ProductIdRequest(치킨.getId())), 사용자아이디);

        //then
        assertThat(cartService.findCartsByCustomerId(사용자아이디))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(
                        new Cart(헌치_치킨.getId(), 헌치_치킨.getQuantity(), 치킨)
                ));
    }

    @DisplayName("해당 회원의 장바구니에 이미 해당 물품이 있을 시 개수를 +1 한다.")
    @Test
    void addCart_plusOne() {
        //given
        Long 사용자아이디 = 사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);

        //when
        cartService.addCarts(List.of(new ProductIdRequest(치킨.getId())), 사용자아이디);
        cartService.addCarts(List.of(new ProductIdRequest(치킨.getId())), 사용자아이디);

        //then
        assertThat(cartService.findCartsByCustomerId(사용자아이디).get(0).getName())
                .isEqualTo("치킨");
        assertThat(cartService.findCartsByCustomerId(사용자아이디).get(0).getQuantity())
                .isEqualTo(2);
    }

    @DisplayName("해당 장바구니 물품 정보를 수정한다")
    @Test
    void patchCart() {
        //given
        Long 사용자아이디 = 사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);
        Long 카트추가 = 카트추가(jdbcTemplate, 헌치_치킨);

        //when
        CartProductInfoResponse cartProductInfoResponses = cartService.patchCart(
                new CartProductInfoRequest(카트추가, 2), 사용자아이디);

        //then
        assertThat(cartProductInfoResponses)
                .usingRecursiveComparison()
                .isEqualTo(new CartProductInfoResponse(헌치_치킨_2.getId(), 2));
    }

    @DisplayName("해당 아이디의 카트들을 삭제한다")
    @Test
    void deleteCarts() {
        //given
        Long 사용자아이디 = 사용자추가(jdbcTemplate, 헌치);
        물품추가(jdbcTemplate, 치킨);
        Long 카트물품_아이디 = 카트추가(jdbcTemplate, 헌치_치킨);

        //when
        cartService.deleteCarts(사용자아이디, List.of(new CartIdRequest(카트물품_아이디)));

        //then
        assertThat(cartService.findCartsByCustomerId(사용자아이디))
                .hasSize(0);
    }
}
