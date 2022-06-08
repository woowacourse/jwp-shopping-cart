package woowacourse;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;

public class Fixtures {

    public static CustomerRequest 조조그린_요청 = new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!");
    public static CustomerRequest 헌치_요청 = new CustomerRequest("hunch@naver.com", "hunch", "1234abcd!");
    public static CustomerLoginRequest 조조그린_로그인_요청 = new CustomerLoginRequest("jo@naver.com", "1234abcd!");
    public static CustomerLoginRequest 헌치_로그인_요청 = new CustomerLoginRequest("hunch@naver.com", "1234abcd!");
    public static Customer 헌치 = new Customer(1L, "hunch@gmail.com", "asdf1234@", "헌치");
    public static Product 치킨 = new Product(1L, "치킨", 10000, "http://example.com/chicken.jpg");
    public static Product 피자 = new Product(2L, "맥주", 20000, "http://example.com/beer.jpg");

    public static final CartEntity 헌치_치킨 = new CartEntity(1L,헌치.getId(), 치킨.getId(), 1);
    public static final CartEntity 헌치_치킨_2 = new CartEntity(1L,헌치.getId(), 치킨.getId(), 2);
    public static final CartEntity 헌치_피자 = new CartEntity(2L,헌치.getId(), 피자.getId(), 1);

    public static Long 물품추가(NamedParameterJdbcTemplate namedParameterJdbcTemplate, Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageUrl)",
                new BeanPropertySqlParameterSource(product), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public static Long 사용자추가(NamedParameterJdbcTemplate namedParameterJdbcTemplate, Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO customer (username, password, nickname, withdrawal) "
                        + "VALUES (:username, :password, :nickname, false)", new BeanPropertySqlParameterSource(customer),
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public static Long 카트추가(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CartEntity cart) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "INSERT INTO cart_item (customer_id, product_id, quantity) VALUES (:customerId, :productId, :quantity)",
                new BeanPropertySqlParameterSource(cart), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public static ExtractableResponse<Response> 회원가입(final CustomerRequest customerRequest) {
        return RestAssured
                .given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signUp")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인(final CustomerLoginRequest customerLoginRequest) {
        return RestAssured
                .given().log().all()
                .body(customerLoginRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_정보조회(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_수정(final CustomerUpdateRequest customerUpdateRequest,
                                                        final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerUpdateRequest)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 비밀번호_변경(PasswordChangeRequest passwordChangeRequest,
                                                        String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passwordChangeRequest)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .extract();
    }

    public static void CREATED(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void BAD_REQUEST(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void OK(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void NO_CONTENT(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void UNAUTHORIZED(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static void FORBIDDEN(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    public static void NOT_FOUND(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void 예외메세지_검증(final ExtractableResponse<Response> response, final String message) {
        assertThat(response.body().asString()).contains(message);
    }
}
