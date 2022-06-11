package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProduct() {
        //when
        ExtractableResponse<Response> response = findProductsApi("");
        List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        //then
        assertThat(productResponses.size()).isEqualTo(12);
        int i = 0;
        for (Products product : Products.values()) {
            assertThat(product.getProduct()).extracting("name", "price", "imageUrl")
                    .containsExactly(productResponses.get(i).getName(), productResponses.get(i).getPrice(),
                            productResponses.get(i++).getImageUrl());
        }
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
