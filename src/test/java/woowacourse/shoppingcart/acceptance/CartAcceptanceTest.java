package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.getAccessToken;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("장바구니 관련 기능")
@Sql(scripts = "classpath:product-data.sql")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "user";
    private Long productId1;
    private Long productId2;

    @DisplayName("장바구니 아이템 1개 추가 후, 장바구니 아이템 목록 조회")
    @Test
    void addCartItem() {
        //given
        String accessToken = getAccessToken();
        장바구니_아이템_추가_요청(accessToken, 1L, 5);

        //when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);
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

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {

    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId, int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String userName, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/{customerName}/carts/{cartId}", userName, cartId)
                .then().log().all()
                .extract();
    }

}
