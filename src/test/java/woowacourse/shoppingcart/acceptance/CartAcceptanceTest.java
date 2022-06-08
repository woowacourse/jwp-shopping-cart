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
import woowacourse.shoppingcart.dto.CartUpdationRequest;
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

    private ValidatableResponse deleteCart(Long productId, String accessToken) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().delete("/users/me/carts/" + productId)
                .then().log().all();
    }

    private ValidatableResponse putCart(Long productId, String accessToken, CartUpdationRequest cartUpdationRequest) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartUpdationRequest)
                .when().put("/users/me/carts/" + productId)
                .then().log().all();
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

        @DisplayName("존재하지 않은 상품을 장바구니에 추가하려고 하면")
        @Nested
        class Context_not_exist_product extends AcceptanceTest {


            @DisplayName("장바구니 추가에 실패하고, 상태코드 404를 반환받는다.")
            @Test
            void it_fail_return_400() {
                ValidatableResponse response = postCart(notExistProductRequest, accessToken);

                response.statusCode(HttpStatus.NOT_FOUND.value());
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

        @Nested
        @DisplayName("장바구니가 비어있다면")
        class Context_empty_carts extends AcceptanceTest {

            @Test
            @DisplayName("장바구니 상품 목록 정보들과 상태코드 200을 반환받는다.")
            void it_return_200() {
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

    @DisplayName("장바구니 상품 삭제 기능")
    @Nested
    class Describe_장바구니_상품_삭제 {
        CartAdditionRequest request1 = new CartAdditionRequest(1L);
        CartAdditionRequest request2 = new CartAdditionRequest(2L);
        CartAdditionRequest request3 = new CartAdditionRequest(3L);

        @Nested
        @DisplayName("유효한 인가로 요청한다면")
        class Context_valid_token extends AcceptanceTest {

            @Test
            @DisplayName("장바구니 상품이 삭제되고 상태코드 204를 반환받는다.")
            void it_return_204() {
                postCart(request1, accessToken);
                postCart(request2, accessToken);
                postCart(request3, accessToken);

                ValidatableResponse response = deleteCart(1L, accessToken);

                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @DisplayName("유효하지 않은 인가로 장바구니를 조회하려고 하면")
        @Nested
        class Context_invalid_token extends AcceptanceTest {

            @DisplayName("장바구니 삭제에 실패하고, 상태코드 401을 반환받는다.")
            @Test
            void it_fail_return_401() {
                ValidatableResponse response = deleteCart(1L, "invalid-token");

                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        @DisplayName("장바구니에 존재하지 않는 상품을 삭제하려고 하면")
        @Nested
        class Context_not_exist_product_in_cart extends AcceptanceTest {

            @DisplayName("장바구니 삭제에 실패하고, 상태코드 400을 반환받는다.")
            @Test
            void it_fail_return_400() {
                ValidatableResponse response = deleteCart(6L, accessToken);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("message", equalTo("장바구니에 상품이 존재하지 않습니다."));
            }
        }

        @DisplayName("존재하지 않는 상품을 삭제하려고 하면")
        @Nested
        class Context_not_exist_product extends AcceptanceTest {

            @DisplayName("장바구니 삭제에 실패하고, 상태코드 404를 반환받는다.")
            @Test
            void it_fail_return_404() {
                ValidatableResponse response = deleteCart(31L, accessToken);

                response.statusCode(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @DisplayName("장바구니 상품 수정 기능")
    @Nested
    class Describe_장바구니_상품_수정 {
        CartAdditionRequest request1 = new CartAdditionRequest(1L);
        CartAdditionRequest request2 = new CartAdditionRequest(2L);
        CartAdditionRequest request3 = new CartAdditionRequest(3L);
        CartUpdationRequest updationRequest = new CartUpdationRequest(3);
        CartUpdationRequest minusUpdationRequest = new CartUpdationRequest(-1);

        @Nested
        @DisplayName("유효한 인가로 요청한다면")
        class Context_valid_token extends AcceptanceTest {

            @Test
            @DisplayName("장바구니 상품이 수정되고 상태코드 200을 반환받는다.")
            void it_return_200() {
                postCart(request1, accessToken);
                postCart(request2, accessToken);
                postCart(request3, accessToken);

                ValidatableResponse response = putCart(1L, accessToken, updationRequest);

                response.statusCode(HttpStatus.OK.value())
                        .body("id", equalTo(1))
                        .body("name", equalTo("붙이는 치약 홀더 / 걸이"))
                        .body("price", equalTo(1600))
                        .body("imageUrl",
                                equalTo("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/163178721379405896.jpg?gif=1&w=512&h=512&c=c"))
                        .body("quantity", equalTo(3));
            }
        }

        @DisplayName("유효하지 않은 인가로 장바구니를 수정하려고 하면")
        @Nested
        class Context_invalid_token extends AcceptanceTest {

            @DisplayName("장바구니 수정에 실패하고, 상태코드 401을 반환받는다.")
            @Test
            void it_fail_return_401() {
                ValidatableResponse response = putCart(1L, "invalid-token", updationRequest);

                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        @DisplayName("장바구니에 존재하지 않는 상품을 수정하려고 하면")
        @Nested
        class Context_not_exist_product_in_cart extends AcceptanceTest {

            @DisplayName("장바구니 수정에 실패하고, 상태코드 400을 반환받는다.")
            @Test
            void it_fail_return_400() {
                ValidatableResponse response = putCart(9L, accessToken, updationRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("message", equalTo("장바구니에 상품이 존재하지 않습니다."));
            }
        }

        @DisplayName("수정하려는 수량이 0보다 작거나 같은 경우")
        @Nested
        class Context_quantity_not_positive extends AcceptanceTest {

            @DisplayName("장바구니 수정에 실패하고, 상태코드 400을 반환받는다.")
            @Test
            void it_fail_return_400() {
                ValidatableResponse response = putCart(9L, accessToken, minusUpdationRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("message", equalTo("잘못된 형식입니다."));
            }
        }

        @DisplayName("수정하려는 상품이 존재하지 않은 상품이라면")
        @Nested
        class Context_not_exist_product extends AcceptanceTest {

            @DisplayName("상태코드 404를 반환받는다.")
            @Test
            void it_return_404() {
                ValidatableResponse response = putCart(22L, accessToken, updationRequest);

                response.statusCode(HttpStatus.NOT_FOUND.value());
            }
        }
    }
}
