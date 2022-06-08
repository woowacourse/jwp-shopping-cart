package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.getAccessToken;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("장바구니 관련 기능")
@Sql(scripts = "classpath:product-data.sql")
public class CartAcceptanceTest extends AcceptanceTest {

    @DisplayName("장바구니 아이템 1개 추가 후, 장바구니 아이템 목록 조회")
    @Test
    void addCartItem() {
        //given
        String accessToken = getAccessToken();
        addCartItemApi(accessToken, 1L, 5);

        //when
        ExtractableResponse<Response> response = findCartItemsApi(accessToken);
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productResponses.size()).isEqualTo(1);
        productResponses.forEach(
                productResponse -> assertThat(productResponse).extracting("id", "name", "price", "imageUrl",
                                "savedQuantity")
                        .containsExactly(1L, "콜드 브루 몰트", 4800,
                                "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001636]_20210225093600536.jpg",
                                5));
    }

    @DisplayName("장바구니 아이템 3개 추가 후, 장바구니에서 1개를 삭제하고 장바구니 목록 조회")
    @Test
    void deleteCartItem() {
        //given
        String accessToken = getAccessToken();
        addCartItemApi(accessToken, 1L, 5);
        addCartItemApi(accessToken, 2L, 5);
        addCartItemApi(accessToken, 3L, 5);

        //when
        ExtractableResponse<Response> deleteResponse = deleteCartItemApi(accessToken, List.of(2L));

        ExtractableResponse<Response> response = findCartItemsApi(accessToken);
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        //then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(productResponses.size()).isEqualTo(2);

    }

    @DisplayName("장바구니에 담긴 아이템의 수량을 수정한다.")
    @Test
    void updateCartItemQuantity() {
        //given
        String accessToken = getAccessToken();
        addCartItemApi(accessToken, 1L, 5);

        //when
        updateCartItemQuantityApi(accessToken, 1L, 10);

        //then
        ExtractableResponse<Response> response = findCartItemsApi(accessToken);
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        assertThat(productResponses.size()).isEqualTo(1);
        productResponses.forEach(productResponse -> assertThat(productResponse.getSavedQuantity()).isEqualTo(10));
    }

    public static ExtractableResponse<Response> addCartItemApi(String accessToken, Long productId, int quantity) {
        CartRequest cartRequest = new CartRequest(productId, quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> findCartItemsApi(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteCartItemApi(String accessToken, List<Long> productIds) {
        ProductIdsRequest productIdsRequest = new ProductIdsRequest(productIds);
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productIdsRequest)
                .when().delete("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> updateCartItemQuantityApi(String accessToken, Long productId,
                                                                          int quantity) {
        CartRequest cartRequest = new CartRequest(productId, quantity);
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().patch("/customers/carts")
                .then().log().all()
                .extract();
    }

}
