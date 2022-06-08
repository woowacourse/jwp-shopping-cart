package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_생성됨;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가_요청;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.getWithToken;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.dto.cart.CartProduct;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.utils.AcceptanceTest;

public class CartControllerTest extends AcceptanceTest {

    private String token;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    public void setUp() {
        super.setUp();

        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        token = loginResponse.jsonPath().getString("accessToken");
        productId1 = 상품_등록되어_있음(치킨, token);
        productId2 = 상품_등록되어_있음(맥주, token);
    }

    @Test
    @DisplayName("장바구니에 아이템을 추가한다.")
    void add_cart() {
        // given
        CartSetRequest request = new CartSetRequest(1000);

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1, request);

        장바구니_아이템_생성됨(response);
    }

    @Test
    @DisplayName("장바구니를 조회한다.")
    void find_cart() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        String token = loginResponse.jsonPath().getString("accessToken");

        CartSetRequest request1 = new CartSetRequest(1000);
        장바구니_아이템_추가_요청(token, productId1, request1);

        CartSetRequest request2 = new CartSetRequest(2000);
        장바구니_아이템_추가_요청(token, productId2, request2);

        // when
        ExtractableResponse<Response> cartResponse = getWithToken("/cart", token);

        // then
        List<CartProduct> list = cartResponse.jsonPath().getList(".", CartProduct.class);
        assertThat(list).hasSize(2)
                .extracting("productId", "quantity")
                .containsExactly(
                        tuple(productId1, 1000),
                        tuple(productId2, 2000)
                );
    }
}
