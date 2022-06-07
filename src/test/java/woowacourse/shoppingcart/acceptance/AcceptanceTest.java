package woowacourse.shoppingcart.acceptance;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class AcceptanceTest {

    static final String BEARER = "Bearer";

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    String findValue(ExtractableResponse<Response> response, String value) {
        return response.body().jsonPath().getString(value);
    }

    String 회원_가입_후_토큰_발급(String account, String password) {
        회원_가입(회원_정보(account, "에덴", password, "에덴 동산", "010", "1111", "2222"));
        return findValue(로그인(account, password), "accessToken");
    }

    Map<String, Object> 회원_정보(String account, String nickname, String password, String address, String start,
                              String middle, String last) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("nickname", nickname);
        request.put("password", password);
        request.put("address", address);
        request.put("phoneNumber", 휴대폰_정보(start, middle, last));
        return request;
    }

    Map<String, String> 휴대폰_정보(String start, String middle, String last) {
        Map<String, String> phoneNumber = new HashMap<>();
        phoneNumber.put("start", start);
        phoneNumber.put("middle", middle);
        phoneNumber.put("last", last);
        return phoneNumber;
    }

    ExtractableResponse<Response> 회원_가입(Map<String, Object> request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/signup")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인(String account, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("password", password);

        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/signin")
                .then()
                .log().all()
                .extract();
    }

    ExtractableResponse<Response> 장바구니_추가(String token, Long productId) {
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

    Long 상품_등록_후_id_반환(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(상품_정보(name, price, imageUrl))
                .when().post("/products")
                .then().log().all()
                .extract();

        return Long.parseLong(response.header(LOCATION).split("/products/")[1]);
    }

    Map<String, Object> 상품_정보(String name, int price, String imageUrl) {
        Map<String, Object> request = new HashMap<>();
        request.put("name", name);
        request.put("price", price);
        request.put("imageUrl", imageUrl);
        return request;
    }

    ExtractableResponse<Response> 회원_탈퇴(String accessToken, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("password", password);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + accessToken)
                .body(request)
                .when().delete("/customers")
                .then().log().all()
                .extract();
    }
}
