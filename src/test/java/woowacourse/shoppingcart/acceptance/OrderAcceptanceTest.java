package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.OrderRequest;

import java.util.List;

import static woowacourse.acceptance.RestAssuredConvenienceMethod.getRequestWithoutToken;
import static woowacourse.acceptance.RestAssuredConvenienceMethod.postRequestWithoutToken;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String URI = "/api/members/아리/orders";

    @DisplayName("주문하기 - 성공한 경우 201 Created가 반환된다.")
    @Test
    void addOrder() {
        OrderRequest request = new OrderRequest(1L, 1);
        postRequestWithoutToken(List.of(request), URI)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("주문하기 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void addBad() {
        OrderRequest request = new OrderRequest(1L, 1);
        postRequestWithoutToken(List.of(request), "/api/members/제이슨/orders")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("주문 내역 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getOrders() {
        postRequestWithoutToken(List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithoutToken(URI)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("주문 내역 조회 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void getBadOrders() {
        getRequestWithoutToken("/api/members/제이슨/orders")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("주문 단일 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getOrder() {
        postRequestWithoutToken(List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithoutToken(URI + "/1")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("주문 단일 조회 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void getBadOrder() {
        getRequestWithoutToken(URI + "/1")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
