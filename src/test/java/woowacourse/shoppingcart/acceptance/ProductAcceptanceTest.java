package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.acceptance.AuthAcceptanceTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("로그인 상태에서 상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given
        // 로그인하여 토큰이 발급 되어 있고
        CustomerAcceptanceTest.회원가입(new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS));
        String token = AuthAcceptanceTest.로그인(new TokenRequest(EMAIL, PASSWORD));

        // when
        // 상품 목록을 조회하면
        ExtractableResponse<Response> response = 상품_목록_조회_요청(token);

        // then
        // 상품 목록이 조회된다.
        List<Product> products = response.body().jsonPath().getList("products", Product.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(products.size()).isEqualTo(3));
    }

    private ExtractableResponse<Response> 상품_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products")
                .then().log().all().extract();
    }
}
