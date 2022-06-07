package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.FindCartItemResponse;
import woowacourse.shoppingcart.dto.SignInRequest;

public class CartAcceptanceTest extends AcceptanceTest {

    @Test
    void 장바구니_상품_추가한뒤_장바구니_상품_조회() {
        var signInResponse = createSignInResult(new SignInRequest("crew01@naver.com", "a12345"), HttpStatus.OK);

        var accessToken = signInResponse.body().jsonPath().getString("token");

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .body(new AddCartItemRequest(1L, 2, true))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        var findAllCartItemResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        var cartItemsResponse = findAllCartItemResponse.body().jsonPath()
                .getList("products", FindCartItemResponse.class);
        var cartItemResponse = cartItemsResponse.get(0);

        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("water"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2),
                () -> assertThat(cartItemResponse.getChecked()).isTrue()
        );
    }
}
