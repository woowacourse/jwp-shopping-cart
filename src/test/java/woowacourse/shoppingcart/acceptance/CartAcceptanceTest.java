package woowacourse.shoppingcart.acceptance;

import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("장바구니 관련 기능")
@Sql("/testData.sql")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "썬";
    private static final String EMAIL = "sun@gmail.com";
    private static final String PASSWORD = "12345678";
    private static final Long productId1 = 1L;
    private static final Long productId2 = 2L;

    //    private Long productId1;
//    private Long productId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // 토큰 발급
        accessToken = requestPostWithBody("/api/login", new TokenRequest(EMAIL, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given, when
        final ExtractableResponse<Response> response =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, productId1);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        requestPostWithTokenAndBody("/api/customer/carts", accessToken, productId1);
        requestPostWithTokenAndBody("/api/customer/carts", accessToken, productId2);

        // when
        final ExtractableResponse<Response> response =
                requestGetWithTokenAndBody("/api/customer/carts", accessToken);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".", CartResponse.class)).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(List.of(
                                new CartResponse(null, productId1, "치킨", 10_000, "http://example.com/chicken.jpg"),
                                new CartResponse(null, productId2, "맥주", 20_000, "http://example.com/beer.jpg")
                        ))
        );
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        // given
        final ExtractableResponse<Response> createResponse =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, productId1);
        final long cartId = parseLong(createResponse.header("Location").split("/carts/")[1]);

        // when
        final ExtractableResponse<Response> response =
                requestDeleteWithTokenAndBody("/api/customer/carts/" + cartId, accessToken, productId1);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/{customerName}/carts", userName)
                .then().log().all()
                .extract();
    }
}
