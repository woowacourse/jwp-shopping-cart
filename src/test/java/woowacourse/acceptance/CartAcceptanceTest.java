package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

@DisplayName("장바구니 관련 기능")
@SpringBootAcceptanceTest
public class CartAcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    @Autowired
    private ProductInsertUtil productInsertUtil;

    @BeforeEach
    public void setUp() {
        productId1 = productInsertUtil.insert("치킨", 10000, "https://example.com/chicken.jpg");
        productId2 = productInsertUtil.insert("맥주", 20000, "https://example.com/beer.jpg");

        String email = "abc@gmailcom";
        String password = "!puterism1";
        String nickname = "puterism";
        RestUtils.signUp(email, password, nickname);
        token = RestUtils.login(email, password)
            .jsonPath()
            .getString("accessToken");
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItem() {
        // given
        int quantity = 2;

        // when
        ExtractableResponse<Response> response = RestUtils.addCartItem(token, productId1, quantity);

        // then
        CartItemResponse result = response.as(CartItemResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(result.getProductId()).isEqualTo(productId1),
            () -> assertThat(result.getName()).isEqualTo("치킨"),
            () -> assertThat(result.getImage()).isEqualTo("https://example.com/chicken.jpg"),
            () -> assertThat(result.getPrice()).isEqualTo(10000),
            () -> assertThat(result.getQuantity()).isEqualTo(quantity)
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {

    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {

    }
}
