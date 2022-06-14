package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.목장갑;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;
import static woowacourse.ShoppingCartFixture.장바구니_URI;
import static woowacourse.ShoppingCartFixture.케이블타이;
import static woowacourse.ShoppingCartFixture.팝업카드;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.ui.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.ui.dto.response.CartResponse;

@DisplayName("장바구니 관련 기능")
@Sql({"/truncate.sql", "/auth.sql", "/product.sql"})
public class CartAcceptanceTest extends AcceptanceTest {
    @DisplayName("장바구니에 상품ID를 전달해서 아이템을 추가할 수 있다")
    @Test
    void addCartItem() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);

        // when
        final ExtractableResponse<Response> 장바구니_추가_전_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final ExtractableResponse<Response> 장바구니담기 = postWithToken(장바구니_URI, 팝업카드, 엑세스토큰);
        final ExtractableResponse<Response> 장바구니_추가_후_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final List<CartResponse> 장바구니_아이템_목록 = 장바구니_추가_후_장바구니조회.body().jsonPath().getList(".", CartResponse.class);

        // then
        assertAll(
                () -> 정상응답_OK(장바구니_추가_전_장바구니조회),
                () -> 비어있음(장바구니_추가_전_장바구니조회),
                () -> 정상응답_OK(장바구니담기),
                () -> 정상응답_OK(장바구니_추가_후_장바구니조회),
                () -> 비어있지않음(장바구니_추가_후_장바구니조회),
                () -> assertThat(장바구니_아이템_목록).extracting("name").containsExactly("배달이 친구들 팝업카드")
        );
    }

    @DisplayName("비어있는 장바구니에 세 종류의 아이템을 추가하고 조회하면 세 종류의 아이템이 조회되어야 한다")
    @Test
    void getCartItems() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 장바구니_추가_전_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final ExtractableResponse<Response> 케이블타이_장바구니담기 = postWithToken(장바구니_URI, 케이블타이, 엑세스토큰);
        final ExtractableResponse<Response> 목장갑_장바구니담기 = postWithToken(장바구니_URI, 목장갑, 엑세스토큰);
        final ExtractableResponse<Response> 팝업카드_장바구니담기 = postWithToken(장바구니_URI, 팝업카드, 엑세스토큰);

        // when
        final ExtractableResponse<Response> 장바구니_추가_후_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final List<CartResponse> 장바구니_아이템들 = 장바구니_추가_후_장바구니조회.body().jsonPath().getList(".", CartResponse.class);

        // then
        assertAll(
                () -> 정상응답_OK(장바구니_추가_전_장바구니조회),
                () -> 비어있음(장바구니_추가_전_장바구니조회),
                () -> 정상응답_OK(케이블타이_장바구니담기),
                () -> 정상응답_OK(목장갑_장바구니담기),
                () -> 정상응답_OK(팝업카드_장바구니담기),
                () -> 정상응답_OK(장바구니_추가_후_장바구니조회),
                () -> 비어있지않음(장바구니_추가_후_장바구니조회),
                () -> assertThat(장바구니_아이템들).extracting("name")
                        .containsExactly("배달이친구들 케이블타이", "을지로 목장갑. 위잉 뚝딱", "배달이 친구들 팝업카드")
        );
    }

    @DisplayName("엑세스토큰, 상품ID, 희망수량을 전달해서 장바구니 내 수량을 수정할 수 있다")
    @Test
    void modifyCartItemQuantity() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 케이블타이_장바구니담기 = postWithToken(장바구니_URI, 케이블타이, 엑세스토큰);
        final ExtractableResponse<Response> 장바구니_추가_후_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final int 케이블타이_수량 = 장바구니_추가_후_장바구니조회.body().jsonPath().getList(".", CartResponse.class).get(0).getQuantity();

        // when
        final int 수정희망수량 = 13;
        final ExtractableResponse<Response> 수량수정응답 = put(장바구니_URI, new CartQuantityUpdateRequest(1L, 수정희망수량), 엑세스토큰);
        final ExtractableResponse<Response> 수정후_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final int 수정후_케이블타이_수량 = 수정후_장바구니조회.body().jsonPath().getList(".", CartResponse.class).get(0).getQuantity();

        // then
        assertAll(
                () -> 정상응답_OK(케이블타이_장바구니담기),
                () -> 정상응답_OK(장바구니_추가_후_장바구니조회),
                () -> assertThat(케이블타이_수량).isOne(),
                () -> 정상응답_OK(수량수정응답),
                () -> 정상응답_OK(수정후_장바구니조회),
                () -> assertThat(수정후_케이블타이_수량).isEqualTo(수정희망수량)
        );
    }

    @DisplayName("장바구니에 아이템 세 종류의 상품을 담고 두 종류의 상품을 삭제하면 하나만 남아야 한다")
    @Test
    void deleteCartItem() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 장바구니_추가_전_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final ExtractableResponse<Response> 케이블타이_장바구니담기 = postWithToken(장바구니_URI, 케이블타이, 엑세스토큰);
        final ExtractableResponse<Response> 목장갑_장바구니담기 = postWithToken(장바구니_URI, 목장갑, 엑세스토큰);
        final ExtractableResponse<Response> 팝업카드_장바구니담기 = postWithToken(장바구니_URI, 팝업카드, 엑세스토큰);
        final ExtractableResponse<Response> 장바구니_추가_후_장바구니조회 = get(장바구니_URI, 엑세스토큰);

        // when
        final ExtractableResponse<Response> 케이블타이_팝업카드_제거 = delete(장바구니_URI, new CartDeleteRequest(List.of(1L, 3L)),
                엑세스토큰);//get(장바구니_URI, 엑세스토큰).jsonPath().getList(".", CartResponse.class).size()
        final ExtractableResponse<Response> 장바구니_제거_후_장바구니조회 = get(장바구니_URI, 엑세스토큰);
        final List<CartResponse> 장바구니_제거_후_아이템_목록 = 장바구니_제거_후_장바구니조회.body().jsonPath().getList(".", CartResponse.class);

        // then
        assertAll(
                () -> 정상응답_OK(장바구니_추가_전_장바구니조회),
                () -> 비어있음(장바구니_추가_전_장바구니조회),
                () -> 정상응답_OK(케이블타이_장바구니담기),
                () -> 정상응답_OK(목장갑_장바구니담기),
                () -> 정상응답_OK(팝업카드_장바구니담기),
                () -> 정상응답_OK(장바구니_추가_후_장바구니조회),
                () -> 비어있지않음(장바구니_추가_후_장바구니조회),
                () -> 삭제성공_NO_CONTENT(케이블타이_팝업카드_제거),
                () -> assertThat(장바구니_제거_후_아이템_목록.size()).isOne(),
                () -> assertThat(장바구니_제거_후_아이템_목록).extracting("name").containsExactly("을지로 목장갑. 위잉 뚝딱")
        );
    }
}
