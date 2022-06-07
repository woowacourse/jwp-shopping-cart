package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인_토큰_발급;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerRegisterRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "1234567891";

    private Long productId1;
    private Long productId2;

    public static ExtractableResponse<Response> 장바구니_상품_추가_요청(final String accessToken, final Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem() {
        // given 회원가입 후 로그인하여
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();

        // when 장바구니에 상품을 담으면
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(accessToken, productId1);

        // then 정상적으로 상품이 추가된다.
        장바구니_상품_추가됨(response);
    }

    @Test
    @DisplayName("장바구니 상품 추가시 존재하지 않는 상품일 경우 404 응답을 반환한다.")
    void addCartItem_invalidProductId_returnsNotFound() {
        //given 회원가입 후 로그인하여
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();

        // when 존재하지 않는 상품을 장바구니에 담으면
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(accessToken, 1000L);

        // then NOT_FOUND 를 응답한다.
        요청이_NOT_FOUND_응답함(response);
    }
}
