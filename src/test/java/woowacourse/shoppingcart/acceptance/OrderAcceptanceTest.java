package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductIdsRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static String testToken;

    private Long productId1;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        signUpCustomer();
        testToken = login();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "https://example.com/chicken.jpg");
        장바구니_아이템_추가_요청(productId1);
    }

    @DisplayName("장바구니 상품을 주문서에 담는다.")
    @Test
    void addOrder() {
        ProductIdsRequest productIdsRequest = new ProductIdsRequest(List.of(productId1));

        RestAssured.given().log().all()
                .body(productIdsRequest)
                .contentType(ContentType.JSON)
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("/orders/1"));
    }


    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers")
                .then().log().all();
    }

    private String login() {
        return RestAssured.given().log().all()
                .body(new TokenRequest("forky", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }

    private static void 장바구니_아이템_추가_요청(Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/cart/{productId}", productId)
                .then().log().all()
                .extract();
    }
}
