package woowacourse.shoppingcart.acceptance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.CartFixture.장바구니_삭제_검증;
import static woowacourse.fixture.CartFixture.장바구니_삭제_요청;
import static woowacourse.fixture.CartFixture.장바구니_아이템_목록_조회_요청;
import static woowacourse.fixture.CartFixture.장바구니_아이템_목록_포함_검증;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_검증;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_요청;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_요청후_ID_반환;
import static woowacourse.fixture.CustomFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomFixture.회원가입_요청_및_ID_추출;
import static woowacourse.fixture.ProductFixture.상품_등록되어_있음;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private String token;
    private long customerId;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        토큰_및_회원_ID_초기화();

        productId1 = 상품_등록되어_있음(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        productId2 = 상품_등록되어_있음(token, "맥주", 20_000, "http://example.com/beer.jpg", 30_000);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, customerId, productId1, 2);

        장바구니_아이템_추가_검증(response);
    }

    @DisplayName("장바구니 상품을 중복하여 담을 경우 예외를 반환한다")
    @Test
    void addCartItemException() {
        장바구니_아이템_추가_요청(token, customerId, productId1, 2);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, customerId, productId1, 2);

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("이미 담겨있는 상품입니다.");
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가_요청후_ID_반환(token, customerId, productId1, 2);
        장바구니_아이템_추가_요청후_ID_반환(token, customerId, productId2, 4);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token, customerId);

        장바구니_아이템_목록_포함_검증(response, productId1, productId2);
    }

    @DisplayName("장바구니 수량 수정 : 정상 요청")
    @Test
    void changeCartItemCount() {
        Long 장바구니_ID = 장바구니_아이템_추가_요청후_ID_반환(token, customerId, productId1, 2);

        ExtractableResponse<Response> response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("count", 10))
                .when()
                .patch(String.format("/api/customers/" + customerId + "/carts?productId=" + 장바구니_ID))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 수량 수정 : 비정상 요청")
    @Test
    void changeCartItemCount_ex() {
        // 장바구니 항목 존재하지 않음
        int 존재하지_않는_장바구니_ID = 50;
        ExtractableResponse<Response> response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("count", 10))
                .when()
                .patch(String.format("/api/customers/%d/carts?%d", customerId, 존재하지_않는_장바구니_ID))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가_요청후_ID_반환(token, customerId, productId1, 2);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, customerId, cartId);

        장바구니_삭제_검증(response);
    }

    private void 토큰_및_회원_ID_초기화() {
        CustomerCreateRequest customerRequest = new CustomerCreateRequest("roma@naver.com", "roma", "12345678");
        this.customerId = 회원가입_요청_및_ID_추출(customerRequest);
        TokenRequest tokenRequest = new TokenRequest("roma@naver.com", "12345678");
        this.token = 로그인_요청_및_토큰발급(tokenRequest);
    }
}
