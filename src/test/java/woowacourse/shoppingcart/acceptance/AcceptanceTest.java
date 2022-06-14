package woowacourse.shoppingcart.acceptance;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.config.SaveAdminRunner;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
@ActiveProfiles("test")
public class AcceptanceTest {

    protected static final int INVALID_FORMAT_ERROR_CODE = 1000;
    protected static final int DUPLICATE_EMAIL_ERROR_CODE = 1001;
    protected static final int INVALID_LOGIN_ERROR_CODE = 1002;
    protected static final int DUPLICATE_CART_ITEM_ERROR_CODE = 1101;
    protected static final int NOT_EXIST_CART_ITEM_ERROR_CODE = 1102;

    @Value("${shopping.admin.email}")
    protected String adminEmail;

    @Value("${shopping.admin.password}")
    protected String adminPassword;

    @LocalServerPort
    private int port;

    @Autowired
    private SaveAdminRunner saveAdminRunner;

    @Autowired
    private ApplicationArguments arguments;

    protected RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        saveAdminRunner.run(arguments);
        spec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    protected ExtractableResponse<Response> 회원가입_요청(String email, String password, String nickname) {
        Map<String, String> body = Map.of(
                "email", email,
                "password", password,
                "nickname", nickname
        );

        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/users")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 로그인_요청(String email, String password) {
        Map<String, String> body = Map.of(
                "email", email,
                "password", password
        );

        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    protected String 토큰_요청(String email, String password) {
        return 로그인_요청(email, password).jsonPath().getString("accessToken");
    }

    protected ExtractableResponse<Response> 회원정보_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get("/users/me")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 회원탈퇴_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete("/users/me")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 회원정보_수정_요청(String nickname, String password, String token) {
        Map<String, String> body = Map.of("nickname", nickname, "password", password);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(body)
                .put("/users/me")
                .then().log().all().extract();
        return response;
    }

    protected ExtractableResponse<Response> 상품_조회(long productId) {
        return RestAssured
            .given().log().all()
            .when().log().all().get("/products/{productId}", productId)
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> 상품_추가(String name, int price, String imageUrl) {
        String adminToken = 토큰_요청(adminEmail, adminPassword);
        Map<String, Object> body = Map.of("name", name, "price", price, "imageUrl", imageUrl);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
            .body(body)
            .when().log().all()
            .post("/products")
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> 장바구니_추가_요청(String token, Long productId) {
        return RestAssured
            .given().log().all()
            .header(org.apache.http.HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(Map.of("productId", productId))
            .when().log().all()
            .post("/users/me/carts")
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> 장바구니_목록_조회(String token) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .get("/users/me/carts")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 장바구니_삭제(String token, Long productId) {
        return RestAssured
            .given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .when().log().all()
            .delete("/users/me/carts/{productId}", productId)
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> 장바구니_수정(String quantity, String token, Long productId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(Map.of("quantity", quantity))
            .when().log().all()
            .put("/users/me/carts/{productId}", productId)
            .then().log().all()
            .extract();
    }
}
