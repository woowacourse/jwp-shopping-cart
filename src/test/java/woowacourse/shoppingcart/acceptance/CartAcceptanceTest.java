package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static woowacourse.shoppingcart.acceptance.AcceptanceUtil.findValue;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("장바구니 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartAcceptanceTest extends AcceptanceTest {

    private static final String CUSTOMER_ACCOUNT = "yeonlog";
    private static final String CUSTOMER_PASSWORD = "abAB12!!";

    @Test
    void 장바구니_추가() {
        // given
        Long productId = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 장바구니_추가(token, productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(LOCATION)).isNotBlank();
    }

    @Test
    void 회원_계정으로_장바구니_조회() {
        // given
        Long productId1 = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록("맥주", 20_000, "http://example.com/beer.jpg");
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);

        장바구니_추가(token, productId1);
        장바구니_추가(token, productId2);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/cart")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 장바구니_삭제() {
        // given
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);
        Long productId = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        장바구니_추가(token, productId);

        Map<String, Object> request = new HashMap<>();
        request.put("productId", productId);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .body(request)
                .when().delete("/customers/cart")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private String 회원_가입_후_토큰_발급(String account, String password) {
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("account", account);
        createRequest.put("nickname", "연로그");
        createRequest.put("password", password);
        createRequest.put("address", "연로그네");

        Map<String, String> phoneNumber = new HashMap<>();
        phoneNumber.put("start", "010");
        phoneNumber.put("middle", "1111");
        phoneNumber.put("last", "1111");
        createRequest.put("phoneNumber", phoneNumber);

        int statusCode = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createRequest)
                .when()
                .post("/signup")
                .then().log().all()
                .extract()
                .statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());

        Map<String, Object> tokenRequest = new HashMap<>();
        tokenRequest.put("account", account);
        tokenRequest.put("password", password);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when()
                .post("/signin")
                .then().log().all()
                .extract();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return findValue(response, "accessToken");
    }

    private ExtractableResponse<Response> 장바구니_추가(String token, Long productId) {
        Map<String, Object> request = new HashMap<>();
        request.put("productId", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .body(request)
                .when().post("/customers/cart")
                .then().log().all()
                .extract();
    }

    private Long 상품_등록(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(상품_정보(name, price, imageUrl))
                .when().post("/products")
                .then().log().all()
                .extract();

        return Long.parseLong(response.header(LOCATION).split("/products/")[1]);
    }

    private Map<String, Object> 상품_정보(String name, int price, String imageUrl) {
        Map<String, Object> request = new HashMap<>();
        request.put("name", name);
        request.put("price", price);
        request.put("imageUrl", imageUrl);
        return request;
    }
}
