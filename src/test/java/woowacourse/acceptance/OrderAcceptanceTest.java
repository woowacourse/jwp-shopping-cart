package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.OrderResponse;

@DisplayName("주문 관련 기능")
@SpringBootAcceptanceTest
public class OrderAcceptanceTest {

    private String token;
    private Long productId1;
    private Long productId2;

    @Autowired
    private ProductInsertUtil productInsertUtil;

    @BeforeEach
    public void setUp() {
        String email = "abc@gmail.com";
        String password = "a1234!";
        String nickname = "does";
        RestUtils.signUp(email, password, nickname);
        token = RestUtils.login(email, password)
            .jsonPath().getString("accessToken");

        productId1 = productInsertUtil.insert("치킨", 20000, "https://test.jpg");
        productId2 = productInsertUtil.insert("콜라", 1500, "https://test.jpg");

        RestUtils.addCartItem(token, productId1, 2);
        RestUtils.addCartItem(token, productId2, 3);
    }

    @DisplayName("주문을 생성한다.")
    @Test
    void addOrder() {
        // when
        ExtractableResponse<Response> response = RestUtils.order(token, List.of(productId1, productId2));

        // then
        OrderResponse result = response.as(OrderResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(result.getId()).isNotNull(),
            () -> assertThat(result.getTotalPrice()).isEqualTo(44500),
            () -> assertThat(result.getOrderDate()).isNotNull(),
            () -> assertThat(result.getOrderDetails())
                .map(CartItemResponse::getProductId)
                .containsOnly(productId1, productId2)
        );
    }

    @DisplayName("주문을 하나 조회한다.")
    @Test
    void getOrder() {
        // given
        ExtractableResponse<Response> extracted = RestUtils.order(token, List.of(productId1, productId2));
        OrderResponse orderResponse = extracted.as(OrderResponse.class);

        // when
        ExtractableResponse<Response> response = RestUtils.getOrder(token, orderResponse.getId());

        // then
        OrderResponse result = response.as(OrderResponse.class);
        assertThat(orderResponse).isEqualTo(result);
    }
}
