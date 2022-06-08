package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    private static final String REQUEST_URL = "/products";

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(REQUEST_URL)
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("productList.id", contains(1, 2, 3, 4, 5))
                .body("productList.name", contains("사과", "포도", "복숭아", "딸기", "오렌지"))
                .body("productList.price", contains(1600, 700, 3100, 2100, 540))
                .body("productList.imageUrl",
                        contains("apple.co.kr", "podo.do", "pea.ch", "strawberry.org", "orange.org"));
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        final Long productId = 2L;

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(REQUEST_URL + "/{productId}", productId)
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("id", equalTo(2))
                .body("name", equalTo("포도"))
                .body("price", equalTo(700))
                .body("imageUrl", equalTo("podo.do"));
    }
}
