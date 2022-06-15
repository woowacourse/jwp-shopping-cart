package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;
import static woowacourse.shoppingcart.application.ProductFixture.바나나_요청;
import static woowacourse.shoppingcart.application.ProductFixture.사과_요청;
import static woowacourse.shoppingcart.application.ProductFixture.장바구니_바나나_요청;
import static woowacourse.shoppingcart.application.ProductFixture.장바구니_사과_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private String token;

    @BeforeEach
    void setup() {
        super.setUp();
        post("/signup", 에덴);
        post("/products", 바나나_요청);
        post("/products", 사과_요청);
        final ExtractableResponse<Response> tokenResponse = post("/signin",
                new TokenRequest("leo0842", "Password123!"));
        token = tokenResponse.jsonPath().getObject(".", TokenResponse.class).getAccessToken();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = post("/customers/cart", token, 장바구니_바나나_요청);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        post("/customers/cart", token, 장바구니_바나나_요청);
        post("/customers/cart", token, 장바구니_사과_요청);

        ExtractableResponse<Response> response = get("/customers/cart", token);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> {
                    List<ProductResponse> cart = response.jsonPath().getList("cart", ProductResponse.class);
                    assertThat(cart.size()).isEqualTo(2);
                }
        );
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        post("/customers/cart", token, 장바구니_바나나_요청);

        ExtractableResponse<Response> response = delete("/customers/cart", token, 장바구니_바나나_요청);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/{customerName}/carts", userName)
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }
}
