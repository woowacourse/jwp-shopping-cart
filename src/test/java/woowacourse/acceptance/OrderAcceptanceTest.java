package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.acceptance.AuthAcceptanceTest.유효한_로그인_요청;
import static woowacourse.acceptance.AuthAcceptanceTest.회원가입_요청;
import static woowacourse.acceptance.CartAcceptanceTest.장바구니_상품_등록;
import static woowacourse.acceptance.CartAcceptanceTest.장바구니_상품_수정;
import static woowacourse.acceptance.CartAcceptanceTest.장바구니_전체_조회_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.setup.AcceptanceTest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.CartDto;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("주문 관련 기능")
class OrderAcceptanceTest extends AcceptanceTest {

    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    @Autowired
    private DatabaseFixture databaseFixture;

    @BeforeEach
    void setup() {
        databaseFixture.save(호박, 고구마, 호박고구마);
    }

    @DisplayName("POST /orders - 장바구니 항목으로 주문")
    @Nested
    class CreateOrderTest {

        @Test
        void 주문_성공시_선택된_상품들을_장바구니에서_제거하며_Location에_생성된_주문의_URI_반환_201() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_복수_등록(토큰, 호박, 고구마, 호박고구마);

            ExtractableResponse<Response> response = 주문_요청(토큰, 호박, 호박고구마);

            CartDto actualCart = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedCart = new CartDto(new Cart(new CartItem(고구마, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).isEqualTo("/orders/1");
            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 장바구니에_등록되지_않은_상품_정보가_주문내역에_포함되면_무시하고_등록된_정보들만_주문하고_201() {
            CartItem 고구마1개 = new CartItem(고구마, 1);
            CartItem 호박고구마0개 = new CartItem(호박고구마, 0);
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_복수_등록(토큰, 호박, 고구마, 호박고구마);
            장바구니_상품_수정(토큰, 호박고구마0개);

            ExtractableResponse<Response> response = 주문_요청(토큰, 호박, 호박고구마);
            CartDto actualCart = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedCart = new CartDto(new Cart(고구마1개, 호박고구마0개));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_복수_등록(토큰, 호박, 고구마);

            OrderRequest requestBody = new OrderRequest(List.of(호박.getId(), 고구마.getId()));
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(requestBody)
                    .when().post("/orders")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        private ExtractableResponse<Response> 주문_요청(String accessToken, Product... products) {
            OrderRequest requestBody = new OrderRequest(toIds(products));
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(requestBody)
                    .when().post("/orders")
                    .then().log().all()
                    .extract();
        }

        private List<Long> toIds(Product[] products) {
            return Arrays.stream(products)
                    .map(Product::getId)
                    .collect(Collectors.toList());
        }
    }

    private void 장바구니_상품_복수_등록(String token, Product... products) {
        for (Product product : products) {
            장바구니_상품_등록(token, product);
        }
    }

    private String 회원가입_후_토큰_생성() {
        회원가입_요청();
        return 유효한_로그인_요청();
    }
}
