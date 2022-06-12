package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.RequestFixture.로그인_및_토큰_발급;
import static woowacourse.fixture.RequestFixture.상품_등록되어_있음;
import static woowacourse.fixture.RequestFixture.장바구니_삭제_요청;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_목록_조회_요청;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_수량_변경_요청;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_추가_요청;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_추가되어_있음;
import static woowacourse.fixture.ResponseFixture.장바구니_삭제됨;
import static woowacourse.fixture.ResponseFixture.장바구니_아이템_목록_응답됨;
import static woowacourse.fixture.ResponseFixture.장바구니_아이템_목록_포함됨;
import static woowacourse.fixture.ResponseFixture.장바구니_아이템_추가됨;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.response.CartItemResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String USER_NAME = "puterism";
    private static final String PASSWORD = "Shopping123!";
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given
        String accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        // then
        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        String accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        // then
        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        // given
        String accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        // when
        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        // then
        장바구니_삭제됨(response);
    }

    @Test
    void 장바구니_아이템_수량_변경() {
        // given
        String accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_수량_변경_요청(accessToken, cartId, 10);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 장바구니에_이미_있는_상품을_담는_경우_하나로_합쳐짐() {
        // given
        String accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        // when
        ExtractableResponse<Response> createResponse = 장바구니_아이템_추가_요청(accessToken, productId1);

        // then
        ExtractableResponse<Response> findResponse = 장바구니_아이템_목록_조회_요청(accessToken);
        CartItemResponse cartItemResponse = findResponse.body().jsonPath().getList("$", CartItemResponse.class).get(0);

        assertAll(
                () -> assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(cartItemResponse.getId()).isEqualTo(cartId),
                () -> assertThat(cartItemResponse.getProductId()).isEqualTo(productId1),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2)
        );
    }
}
