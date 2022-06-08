package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.addCartItemApi;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
@Sql(scripts = "classpath:product-data.sql")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProduct() {
        //when
        ExtractableResponse<Response> response = findProductsApi("");
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        //then
        assertThat(productResponses.size()).isEqualTo(12);
        productResponses.forEach(product -> assertThat(product.getSavedQuantity()).isEqualTo(0));
    }

    @DisplayName("상품을 장바구니에 담은 후, 상품 목록을 조회한다.")
    @Test
    void findProductsWhenAddCartItem() {
        //given
        String accessToken = getAccessToken();
        addCartItemApi(accessToken, 1L, 5);

        //when
        ExtractableResponse<Response> response = findProductsApi(accessToken);
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        //then
        assertThat(productResponses.size()).isEqualTo(12);
        boolean match = productResponses.stream()
                .anyMatch(productResponse -> productResponse.getId().equals(1L)
                        && productResponse.getSavedQuantity().equals(5));
        assertThat(match).isTrue();

    }

    public static String getAccessToken() {
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");

        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();

        return RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/auth/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> findProductsApi(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
