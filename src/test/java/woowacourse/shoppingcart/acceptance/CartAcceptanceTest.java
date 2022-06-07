package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.FindCartItemResponse;
import woowacourse.shoppingcart.dto.SignInRequest;

public class CartAcceptanceTest extends AcceptanceTest {

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_조회_장바구니_삭제한뒤_다시_조회() {
        String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        var findAllCartItemResponse = findCart(accessToken);

        checkFindResult(findAllCartItemResponse);

        deleteCart(accessToken, 1L, HttpStatus.NO_CONTENT);

        findAllCartItemResponse = findCart(accessToken);

        assertThat(findAllCartItemResponse.size()).isEqualTo(0);
    }

    private void checkFindResult(List<FindCartItemResponse> findAllCartItemResponse) {
        var cartItemResponse = findAllCartItemResponse.get(0);

        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("water"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2),
                () -> assertThat(cartItemResponse.getChecked()).isTrue()
        );
    }

    private ExtractableResponse<Response> deleteCart(String accessToken, Long cartItemId, HttpStatus httpStatus) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new DeleteCartItemRequest(Map.of("id", cartItemId)))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/cart")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    private List<FindCartItemResponse> getFindCartItemResponses(ExtractableResponse<Response> findAllCartItemResponse) {
        return findAllCartItemResponse.body().jsonPath()
                .getList("products", FindCartItemResponse.class);
    }

    private void createCart(String accessToken, Long productId) {
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .body(new AddCartItemRequest(productId, 2, true))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    private List<FindCartItemResponse> findCart(String accessToken) {
        var findAllCartItemResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return getFindCartItemResponses(findAllCartItemResponse);
    }

    @Test
    void 장바구니_상품_추가한뒤_유효하지_않은_장바구니_아이템_아이디로_삭제할_경우() {
        String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        var invalidCartItemId = 2L;
        var response = deleteCart(accessToken, invalidCartItemId, HttpStatus.BAD_REQUEST);

        var message = response.body().jsonPath().getString("message");
        assertThat(message).contains("[ERROR]", "장바구니", "아이템", "없");
    }

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_조회_장바구니_전체_삭제한뒤_다시_조회() {
        String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        createCart(accessToken, 2L);

        var findAllCartItemResponse = findCart(accessToken);

        checkFindResults(findAllCartItemResponse);

        deleteAllCart(accessToken, 1L, HttpStatus.NO_CONTENT);

        findAllCartItemResponse = findCart(accessToken);

        assertThat(findAllCartItemResponse.size()).isEqualTo(0);
    }

    private void checkFindResults(List<FindCartItemResponse> findAllCartItemResponse) {
        var cartItemResponse1 = findAllCartItemResponse.get(0);
        var cartItemResponse2 = findAllCartItemResponse.get(1);

        assertAll(
                () -> assertThat(cartItemResponse1.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse1.getName()).isEqualTo("water"),
                () -> assertThat(cartItemResponse1.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItemResponse1.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse1.getQuantity()).isEqualTo(2),
                () -> assertThat(cartItemResponse1.getChecked()).isTrue(),
                () -> assertThat(cartItemResponse2.getId()).isEqualTo(2L),
                () -> assertThat(cartItemResponse2.getName()).isEqualTo("cheese"),
                () -> assertThat(cartItemResponse2.getPrice()).isEqualTo(2000),
                () -> assertThat(cartItemResponse2.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse2.getQuantity()).isEqualTo(2),
                () -> assertThat(cartItemResponse2.getChecked()).isTrue()
        );
    }

    private void deleteAllCart(String accessToken, Long customerId, HttpStatus httpStatus) {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .delete("/cart/all")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    private String getAccessToken() {
        var signInResponse = createSignInResult(new SignInRequest("crew01@naver.com", "a12345"), HttpStatus.OK);

        return signInResponse.body().jsonPath().getString("token");
    }
}
