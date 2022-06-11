package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.acceptance.AuthAcceptanceTest.유효한_로그인_요청;
import static woowacourse.acceptance.AuthAcceptanceTest.회원가입_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.common.dto.RedirectErrorResponse;
import woowacourse.setup.AcceptanceTest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.UpdateCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.response.CartDto;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("장바구니 관련 기능")
class CartAcceptanceTest extends AcceptanceTest {

    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    @Autowired
    private DatabaseFixture databaseFixture;

    @BeforeEach
    void setup() {
        databaseFixture.save(호박, 고구마, 호박고구마);
    }

    @DisplayName("GET /cart - 고객 본인의 장바구니 전체 조회")
    @Nested
    class GetAllCartItemsTest {

        @Test
        void 로그인된_고객의_장바구니에_담긴_모든_상품과_수량_정보_조회_200() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);
            장바구니_상품_등록(토큰, 고구마);

            ExtractableResponse<Response> response = 장바구니_전체_조회_요청(토큰);
            CartDto actualResponseBody = response.as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(
                    new CartItem(호박, 1), new CartItem(고구마, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 장바구니가_비어있어도_200() {
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_전체_조회_요청(토큰);
            CartDto actualResponseBody = response.as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .when().get("/cart")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @DisplayName("DELETE /cart - 고객 본인의 장바구니 비우기")
    @Nested
    class EmptyCartTest {

        @Test
        void 로그인된_고객의_장바구니를_비우고_204() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);

            ExtractableResponse<Response> response = 장바구니_비우기(토큰);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 장바구니가_이미_비어있는_상태였어도_204() {
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_비우기(토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .when().delete("/cart")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        private ExtractableResponse<Response> 장바구니_비우기(String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().delete("/cart")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("POST /cart/:productId - 고객의 장바구니에 상품 등록")
    @Nested
    class RegisterCartItemTest {

        @Test
        void 로그인된_고객의_장바구니에_상품_등록하고_201() {
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_상품_등록(토큰, 호박);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(new CartItem(호박, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 이미_장바구니에_등록된_상품을_등록하려는_경우_실패하며_400() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);

            ExtractableResponse<Response> response = 장바구니_상품_등록(토큰, 호박);
            RedirectErrorResponse actualResponse = response.as(RedirectErrorResponse.class);
            RedirectErrorResponse expectedResponse = new RedirectErrorResponse("이미 장바구니에 담긴 상품입니다.");
            CartDto actualCart = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedCart = new CartDto(new Cart(new CartItem(호박, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(actualResponse).isEqualTo(expectedResponse);
            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 존재하지_않는_상품을_등록하려는_경우_404() {
            Product 존재하지_않는_전설의_고구마 = new Product(4L, "전설의 고구마", 10000000, "이미지");
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_상품_등록(토큰, 존재하지_않는_전설의_고구마);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .when().post("/cart/" + 1L)
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @DisplayName("PUT /cart/:productId/quantity - 고객의 장바구니에 상품 수량 수정")
    @Nested
    class UpdateCartItemQuantityTest {

        @Test
        void 로그인된_고객의_장바구니에_이미_등록된_상품인_경우_수량을_수정하고_200() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);
            CartItem 호박_100개 = new CartItem(호박, 100);

            ExtractableResponse<Response> response = 장바구니_상품_수정(토큰, 호박_100개);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(호박_100개));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 로그인된_고객의_장바구니에_등록되지_않은_상품인_경우_수량_정보와_함께_새로_등록하고_200() {
            String 토큰 = 회원가입_후_토큰_생성();
            CartItem 호박_100개 = new CartItem(호박, 100);

            ExtractableResponse<Response> response = 장바구니_상품_수정(토큰, 호박_100개);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(호박_100개));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 존재하지_않는_상품을_등록하려는_경우_404() {
            Product 존재하지_않는_전설의_고구마 = new Product(4L, "전설의 고구마", 10000000, "이미지");
            CartItem 전설의_고구마_100개 = new CartItem(존재하지_않는_전설의_고구마, 100);
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_상품_수정(토큰, 전설의_고구마_100개);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);

            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UpdateCartItemQuantityRequest(100))
                    .when().put("/cart/" + 호박.getId() + "/quantity")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @DisplayName("DELETE /cart/products - 장바구니 상품 부분 제거")
    @Nested
    class RemoveCartItemsTest {

        @Test
        void 선택된_상품들만_장바구니에서_제거_204() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);
            장바구니_상품_등록(토큰, 고구마);
            장바구니_상품_등록(토큰, 호박고구마);

            ExtractableResponse<Response> response = 장바구니_상품_부분_제거(토큰, 호박, 호박고구마);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(new CartItem(고구마, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 장바구니에_등록되지_않은_상품_정보가_포함되면_무시하고_등록된_정보들만_전부_제거_204() {
            String 토큰 = 회원가입_후_토큰_생성();
            장바구니_상품_등록(토큰, 호박);
            장바구니_상품_등록(토큰, 고구마);

            ExtractableResponse<Response> response = 장바구니_상품_부분_제거(토큰, 호박, 호박고구마);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart(new CartItem(고구마, 1)));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 존재하지_않은_상품을_제거하려는_경우에도_결과는_차이가_없으므로_204() {
            Product 존재하지_않는_전설의_고구마 = new Product(4L, "전설의 고구마", 10000000, "이미지");
            String 토큰 = 회원가입_후_토큰_생성();

            ExtractableResponse<Response> response = 장바구니_상품_부분_제거(토큰, 존재하지_않는_전설의_고구마);
            CartDto actualResponseBody = 장바구니_전체_조회_요청(토큰).as(CartDto.class);
            CartDto expectedResponseBody = new CartDto(new Cart());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void 삭제_대상_정보가_누락된_경우_400() {
            String 토큰 = 회원가입_후_토큰_생성();
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .auth().oauth2(토큰)
                    .params("id", "")
                    .when().delete("/cart/products")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 로그인_되어있지_않은_경우_401() {
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .params("id", "1,2")
                    .when().delete("/cart/products")
                    .then().log().all()
                    .extract();
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        private ExtractableResponse<Response> 장바구니_상품_부분_제거(String accessToken, Product... products) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .params("id", toIdList(products))
                    .when().delete("/cart/products" )
                    .then().log().all()
                    .extract();
        }

        private String toIdList(Product... products) {
            return Arrays.stream(products)
                    .map(it -> it.getId() + "")
                    .collect(Collectors.joining(","));
        }
    }

    private String 회원가입_후_토큰_생성() {
        회원가입_요청();
        return 유효한_로그인_요청();
    }

    static ExtractableResponse<Response> 장바구니_전체_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    static ExtractableResponse<Response> 장바구니_상품_등록(String accessToken, Product product) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().post("/cart/" + product.getId())
                .then().log().all()
                .extract();
    }

    static ExtractableResponse<Response> 장바구니_상품_수정(String accessToken, CartItem cartItem) {
        Long productId = cartItem.getProductId();
        UpdateCartItemQuantityRequest requestBody = new UpdateCartItemQuantityRequest(cartItem.getQuantity());
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().put("/cart/" + productId + "/quantity")
                .then().log().all()
                .extract();
    }
}
