package woowacourse.shoppingcart.acceptance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.CartFixture.장바구니_삭제_검증;
import static woowacourse.fixture.CartFixture.장바구니_상품_목록_조회_요청;
import static woowacourse.fixture.CartFixture.장바구니_상품_삭제_요청;
import static woowacourse.fixture.CartFixture.장바구니_상품_추가_검증;
import static woowacourse.fixture.CartFixture.장바구니_상품_추가_요청;
import static woowacourse.fixture.CartFixture.장바구니_상품_추가_요청후_ID_반환;
import static woowacourse.fixture.CustomerFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomerFixture.회원가입_요청_및_ID_추출;
import static woowacourse.fixture.Fixture.covertTypeList;
import static woowacourse.fixture.ProductFixture.상품_등록후_ID반환;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.cartitem.CartResponse;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private String token;
    private long customerId;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        토큰_및_회원_ID_초기화();

        productId1 = 상품_등록후_ID반환(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        productId2 = 상품_등록후_ID반환(token, "맥주", 20_000, "http://example.com/beer.jpg", 30_000);
    }

    @DisplayName("장바구니 상품 추가 : 정상")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, customerId, productId1, 2);

        장바구니_상품_추가_검증(response);
    }

    @DisplayName("장바구니 상품 추가 : 비정상 - 없는 상품 ID")
    @Test
    void addCartItem_ex_non_exist_product_id() {
        long 미존재_상품_ID = 50;
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, customerId, 미존재_상품_ID, 2);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("존재하지 않는 상품 ID입니다.");
    }

    @DisplayName("장바구니 상품 추가 : 비정상 - 재고 수량 부족")
    @Test
    void addCartItem_ex_non_exist_over_quantity() {
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, customerId, productId1, 20_001);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("재고가 부족합니다.")
        );
    }

    @DisplayName("장바구니 상품을 중복하여 담을 경우 예외를 반환한다")
    @Test
    void addCartItemException() {
        장바구니_상품_추가_요청(token, customerId, productId1, 2);
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, customerId, productId1, 2);

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("이미 담겨있는 상품입니다.");
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_상품_추가_요청후_ID_반환(token, customerId, productId1, 2);
        장바구니_상품_추가_요청후_ID_반환(token, customerId, productId2, 4);

        ExtractableResponse<Response> response = 장바구니_상품_목록_조회_요청(token, customerId);

        CartResponse expected1 = new CartResponse(30 + 1L, "http://example.com/chicken.jpg", "치킨", 10_000, 20_000, 2);
        CartResponse expected2 = new CartResponse(30 + 2L, "http://example.com/beer.jpg", "맥주", 20_000, 30_000, 4);
        List<CartResponse> cartResponses = covertTypeList(response, CartResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(cartResponses.size()).isEqualTo(2L);
        assertThat(cartResponses).containsExactlyInAnyOrder(expected1, expected2);
    }

    @DisplayName("장바구니 수량 수정")
    @Nested
    class UpdateCart {

        @DisplayName("- 정상 요청")
        @Test
        void changeCartItemCount() {
            장바구니_상품_추가_요청후_ID_반환(token, customerId, productId1, 2);

            ExtractableResponse<Response> response = given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + token)
                    .body(Map.of("count", 10))
                    .when()
                    .patch("/api/customers/" + customerId + "/carts?productId=" + productId1)
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @DisplayName("- 비정상 요청 : 존재하지 않는 상품 ID")
        @Test
        void changeCartItemCount_ex_non_exist() {
            int 존재하지_않는_상품_ID = 50;
            ExtractableResponse<Response> response = given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + token)
                    .body(Map.of("count", 10))
                    .when()
                    .patch("/api/customers/" + customerId + "/carts?productId=" + 존재하지_않는_상품_ID)
                    .then().log().all()
                    .extract();
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(errorResponse.getMessage()).isEqualTo("존재하지 않는 상품 ID입니다.");
        }

        @DisplayName("- 비정상 요청 : 재고보다 많은 수량 수정 시도")
        @Test
        void changeCartItemCount_ex_over_quantity() {
            long 장바구니_ID = 장바구니_상품_추가_요청후_ID_반환(token, customerId, productId1, 2);

            ExtractableResponse<Response> response = given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + token)
                    .body(Map.of("count", 20_001))
                    .when()
                    .patch("/api/customers/" + customerId + "/carts?productId=" + 장바구니_ID)
                    .then().log().all()
                    .extract();
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(errorResponse.getMessage()).isEqualTo("재고가 부족합니다.");
        }
    }

    @DisplayName("장바구니 삭제 : 정상")
    @Test
    void deleteCartItem() {
        long productId = 8; // 1이 아닌 값을 넣어야 cart_id 가 아닌 product_id에 대해 테스트를 할 수 있다
        장바구니_상품_추가_요청(token, customerId, productId, 2);

        ExtractableResponse<Response> response = 장바구니_상품_삭제_요청(token, customerId, productId);

        장바구니_삭제_검증(response);
    }

    @DisplayName("장바구니 삭제 : 비정상 - 존재하지 않는 장바구니")
    @Test
    void deleteCartItem_exception_non_exist_cart() {
        장바구니_상품_추가_요청후_ID_반환(token, customerId, productId1, 2);

        long 존재하지않는_장바구니_ID = 50;
        ExtractableResponse<Response> response = 장바구니_상품_삭제_요청(token, customerId, 존재하지않는_장바구니_ID);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("존재하지 않는 상품 ID입니다.");
    }

    private void 토큰_및_회원_ID_초기화() {
        CustomerCreateRequest customerRequest = new CustomerCreateRequest("philz@gmail.com", "swcho", "1q2w3e4r!");
        this.customerId = 회원가입_요청_및_ID_추출(customerRequest);
        TokenRequest tokenRequest = new TokenRequest("philz@gmail.com", "1q2w3e4r!");
        this.token = 로그인_요청_및_토큰발급(tokenRequest);
    }
}
