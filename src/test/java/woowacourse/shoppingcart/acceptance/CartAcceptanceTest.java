package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.FindCartItemResponse;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;

public class CartAcceptanceTest extends AcceptanceTest {

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_조회_장바구니_삭제한뒤_다시_조회() {
        final String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        var findAllCartItemResponse = findCart(accessToken);

        checkFindResult(findAllCartItemResponse);

        deleteCart(accessToken, 1L, HttpStatus.NO_CONTENT);

        findAllCartItemResponse = findCart(accessToken);

        assertThat(findAllCartItemResponse.size()).isEqualTo(0);
    }

    private void checkFindResult(final List<FindCartItemResponse> findAllCartItemResponse) {
        final var cartItemResponse = findAllCartItemResponse.get(0);

        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("water"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2),
                () -> assertThat(cartItemResponse.getChecked()).isTrue()
        );
    }

    private ExtractableResponse<Response> deleteCart(final String accessToken, final Long cartItemId,
                                                     final HttpStatus httpStatus) {
        final var deleteCartItemRequest = new DeleteCartItemRequest(cartItemId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new DeleteCartItemRequests(List.of(deleteCartItemRequest)))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/cart")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    private List<FindCartItemResponse> getFindCartItemResponses(
            final ExtractableResponse<Response> findAllCartItemResponse) {
        return findAllCartItemResponse.body().jsonPath()
                .getList("cartItems", FindCartItemResponse.class);
    }

    private void createCart(final String accessToken, final Long productId) {
        final var jsonObject = new JSONObject();

        try {
            jsonObject.put("productId", productId);
            jsonObject.put("quantity", 2);
            jsonObject.put("checked", true);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .body(jsonObject)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    private List<FindCartItemResponse> findCart(final String accessToken) {
        final var findAllCartItemResponse = RestAssured.given().log().all()
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
        final String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        final var invalidCartItemId = 2L;
        final var response = deleteCart(accessToken, invalidCartItemId, HttpStatus.BAD_REQUEST);

        final var message = response.body().jsonPath().getString("message");
        assertThat(message).contains("[ERROR]", "장바구니", "아이템", "없");
    }

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_조회_장바구니_전체_삭제한뒤_다시_조회() {
        final String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        createCart(accessToken, 2L);

        var findAllCartItemResponse = findCart(accessToken);

        checkFindResults(findAllCartItemResponse);

        deleteAllCart(accessToken, HttpStatus.NO_CONTENT);

        findAllCartItemResponse = findCart(accessToken);

        assertThat(findAllCartItemResponse.size()).isEqualTo(0);
    }

    private void checkFindResults(final List<FindCartItemResponse> findAllCartItemResponse) {
        final var cartItemResponse1 = findAllCartItemResponse.get(0);
        final var cartItemResponse2 = findAllCartItemResponse.get(1);

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

    private void deleteAllCart(final String accessToken, final HttpStatus httpStatus) {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .delete("/cart/all")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    private String getAccessToken() {
        final var signInResponse = createSignInResult(new SignInRequest("crew01@naver.com", "a12345"), HttpStatus.OK);

        return signInResponse.body().jsonPath().getString("token");
    }

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_수정() {
        final String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        createCart(accessToken, 2L);

        final var response = updateCartItem(1L, 2L, accessToken, HttpStatus.OK);

        checkUpdateResult(response);
    }

    private void checkUpdateResult(final ExtractableResponse<Response> response) {
        final var findCartItemResponses = getFindCartItemResponses(response);
        final var cartItemResponse1 = findCartItemResponses.get(0);
        final var cartItemResponse2 = findCartItemResponses.get(1);

        assertAll(
                () -> assertThat(cartItemResponse1.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse1.getName()).isEqualTo("water"),
                () -> assertThat(cartItemResponse1.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItemResponse1.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse1.getQuantity()).isEqualTo(10),
                () -> assertThat(cartItemResponse1.getChecked()).isFalse(),
                () -> assertThat(cartItemResponse2.getId()).isEqualTo(2L),
                () -> assertThat(cartItemResponse2.getName()).isEqualTo("cheese"),
                () -> assertThat(cartItemResponse2.getPrice()).isEqualTo(2000),
                () -> assertThat(cartItemResponse2.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse2.getQuantity()).isEqualTo(5),
                () -> assertThat(cartItemResponse2.getChecked()).isTrue()
        );
    }

    private ExtractableResponse<Response> updateCartItem(final Long cartId1, final Long cartId2,
                                                         final String accessToken,
                                                         final HttpStatus httpStatus) {
        final var updateCartItemRequests = List.of(
                new UpdateCartItemRequest(cartId1, 10, false),
                new UpdateCartItemRequest(cartId2, 5, true)
        );

        final var updateCartItemsRequest = new UpdateCartItemRequests(updateCartItemRequests);
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(updateCartItemsRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/cart")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    @Test
    void 장바구니_상품_추가한뒤_존재하지_않는_장바구니_아이디로_상품_수정_요청하는_경우() {
        final String accessToken = getAccessToken();

        createCart(accessToken, 1L);

        createCart(accessToken, 2L);

        final var invalidCartId = 100L;
        final var response = updateCartItem(1L, invalidCartId, accessToken, HttpStatus.BAD_REQUEST);

        final var message = response.body().jsonPath().getString("message");
        assertThat(message).contains("[ERROR]", "장바구니", "아이템", "없");
    }
}
