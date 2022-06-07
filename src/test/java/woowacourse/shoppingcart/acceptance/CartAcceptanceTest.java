package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CartAdditionRequest;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void set() {
        String email = "kun@naver.com";
        String password = "1q2w3e4r";
        CustomerCreationRequest customerRequest = new CustomerCreationRequest(email, password, "kun");

        postUser(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }

    @DisplayName("장바구니 상품 추가 기능")
    @Nested
    class Describe_장바구니_상품_추가 {
        CartAdditionRequest request = new CartAdditionRequest(1L);
        CartAdditionRequest notExistProductRequest = new CartAdditionRequest(21L);

        @DisplayName("유효한 인가와 장바구니에 추가되지 않은 상품을 추가하면")
        @Nested
        class Context_validate_token_not_added_in_cart extends AcceptanceTest {

            @DisplayName("상태코드 204를 반환받는다.")
            @Test
            void it_add_product_return_200() {
                ValidatableResponse response = postCart(request, accessToken);

                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @DisplayName("유효한 인가와 장바구니에 추가되지 않은 상품을 추가하면")
        @Nested
        class Context_not_exist_product extends AcceptanceTest {


            @DisplayName("장바구니 추가에 실패하고, 상태코드 400을 반환받는다.")
            @Test
            void it_fail_return_400() {
                ValidatableResponse response = postCart(notExistProductRequest, accessToken);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("message", equalTo("물품이 존재하지 않습니다."));
            }
        }

        @DisplayName("유효하지 않은 인가로 장바구니에 상품을 추가하려고 하면")
        @Nested
        class Context_invalid_token extends AcceptanceTest {

            @DisplayName("장바구니 추가에 실패하고, 상태코드 401을 반환받는다.")
            @Test
            void it_fail_return_401() {
                ValidatableResponse response = postCart(request, "invalid-token");

                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        @DisplayName("중복된 상품을 장바구니에 담으려고 하면")
        @Nested
        class Context_duplicated_product_in_cart extends AcceptanceTest {

            @DisplayName("장바구니 추가에 실패하고, 상태코드 400을 반환받는다.")
            @Test
            void it_fail_return_400() {
                postCart(request, accessToken);
                ValidatableResponse duplicatedResponse = postCart(request, accessToken);

                duplicatedResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("message", equalTo("중복된 물품입니다."));
            }
        }
    }

    @DisplayName("장바구니 상품 목록 조회 기능")
    @Nested
    class Describe_장바구니_상품_목록_조회 {
        CartAdditionRequest request1 = new CartAdditionRequest(1L);
        CartAdditionRequest request2 = new CartAdditionRequest(2L);
        CartAdditionRequest request3 = new CartAdditionRequest(3L);

        @Nested
        @DisplayName("유효한 인가로 요청한다면")
        class Context_valid_token extends AcceptanceTest {

            @Test
            @DisplayName("장바구니 상품 목록 정보들과 상태코드 200을 반환받는다.")
            void it_return_carts_200() {
                postCart(request1, accessToken);
                postCart(request2, accessToken);
                postCart(request3, accessToken);

                ValidatableResponse response = getCart(accessToken);

                response.statusCode(HttpStatus.OK.value());
            }
        }

        @DisplayName("유효하지 않은 인가로 장바구니를 조회하려고 하면")
        @Nested
        class Context_invalid_token extends AcceptanceTest {

            @DisplayName("장바구니 추가에 실패하고, 상태코드 401을 반환받는다.")
            @Test
            void it_fail_return_401() {
                ValidatableResponse response = getCart("invalid-token");

                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }

    private ValidatableResponse postCart(CartAdditionRequest request, String accessToken) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION,
                        AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users/me/carts")
                .then().log().all();
    }

    private ValidatableResponse getCart(String accessToken) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().get("/users/me/carts")
                .then().log().all();
    }
}
