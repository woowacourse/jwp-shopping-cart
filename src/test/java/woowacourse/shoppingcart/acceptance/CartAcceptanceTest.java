package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.fixture.CartSimpleAssured.장바구니_상품_등록;
import static woowacourse.shoppingcart.acceptance.fixture.CartSimpleAssured.장바구니_상품_삭제;
import static woowacourse.shoppingcart.acceptance.fixture.CartSimpleAssured.장바구니_상품_수량_수정;
import static woowacourse.shoppingcart.acceptance.fixture.CartSimpleAssured.장바구니_상품_조회;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_등록;
import static woowacourse.shoppingcart.acceptance.fixture.UserSimpleAssured.회원가입_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.fixture.UserSimpleAssured;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private String 로그인된_토큰;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        String email = "tonic@email.com";
        String password = "12345678a";
        String nickname = "토닉";

        회원가입_요청(new SignUpRequest(email, password, nickname));
        로그인된_토큰 = UserSimpleAssured.토큰_요청(new TokenRequest(email, password));
    }

    @DisplayName("장바구니 상품을 추가할 때 정상 케이스일 경우 204를 응답한다.")
    @Test
    void addCartItemNoContent() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));

        ExtractableResponse<Response> response = 장바구니_상품_등록(로그인된_토큰, "1");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 상품을 추가할 때 인가가 잘못되면 401을 응답한다.")
    @Test
    void addCartItemUnAuthorized() {
        ExtractableResponse<Response> response = 장바구니_상품_등록("invalidToken", "1L");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("존재하지 않는 상품을 장바구니에 담을 경우 400을 응답한다.")
    @Test
    void addCartItemNotFoundProduct() {
        ExtractableResponse<Response> response = 장바구니_상품_등록(로그인된_토큰, "1");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo("물품이 존재하지 않습니다.");
    }

    @DisplayName("중복된 상품을 장바구니에 담을 경우 400을 응답한다.")
    @Test
    void addCartItemDuplicateProduct() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));

        장바구니_상품_등록(로그인된_토큰, "1");
        ExtractableResponse<Response> response = 장바구니_상품_등록(로그인된_토큰, "1");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("message")).isEqualTo("중복된 물품입니다.");
    }

    @DisplayName("장바구니 상품을 조회할 때 인가가 잘못되면 401을 응답한다.")
    @Test
    void getCartItemsUnauthorized() {
        ExtractableResponse<Response> response = 장바구니_상품_조회("invalidToken");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니 상품을 조회할 때 정상 케이스인 경우 장바구니 상품 목록과 200을 응답한다.")
    @Test
    void getCartItemsOk() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));

        장바구니_상품_등록(로그인된_토큰, "1");
        ExtractableResponse<Response> response = 장바구니_상품_조회(로그인된_토큰);
        List<CartItemResponse> list = response.jsonPath().getList("cartList", CartItemResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(list).hasSize(1)
        );
    }

    @DisplayName("장바구니 상품을 삭제할 때 인가가 잘못되면 401을 응답한다.")
    @Test
    void deleteCartItemUnauthorized() {
        ExtractableResponse<Response> response = 장바구니_상품_삭제("invalid token", 1L);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니에 없는 상품을 삭제할 때 400을 응답한다.")
    @Test
    void deleteCartItemBadRequest() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));

        ExtractableResponse<Response> response = 장바구니_상품_삭제(로그인된_토큰, 1L);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("장바구니에 상품을 삭제할 때 없는 상품인 경우 404를 응답한다.")
    @Test
    void deleteCartItemNotFound() {
        ExtractableResponse<Response> response = 장바구니_상품_삭제(로그인된_토큰, 1L);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니에 상품을 삭제할 때 정상 케이스인 경우 상품을 삭제하고 204를 응답한다.")
    @Test
    void deleteCartItemNoContent() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));
        장바구니_상품_등록(로그인된_토큰, "1");
        ExtractableResponse<Response> response = 장바구니_상품_삭제(로그인된_토큰, 1L);

        List<Object> cartList = 장바구니_상품_조회(로그인된_토큰).jsonPath()
                .getList("cartList");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(cartList).hasSize(0)
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 수정할 때 인가되지 않은 회원이면 401을 응답한다.")
    @Test
    void putCartItemUnauthorized() {
        ExtractableResponse<Response> response = 장바구니_상품_수량_수정("invalid token", "1", 1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 수정할 때 없는 상품이면 404을 응답한다.")
    @Test
    void putCartItemNotFound() {
        ExtractableResponse<Response> response = 장바구니_상품_수량_수정(로그인된_토큰, "1", 2);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 수정할 때 장바구니에 없는 상품이면 400을 응답한다.")
    @Test
    void putCartItemNotFoundProductInCartBadRequest() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));

        ExtractableResponse<Response> response = 장바구니_상품_수량_수정(로그인된_토큰, "1", 2);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message"))
                        .isEqualTo("장바구니에 상품이 존재하지 않습니다."),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(1102)
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 수정할 때 수량이 음수이면 400을 응답한다.")
    @Test
    void putCartItemInvalidQuantityBadRequest() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));
        장바구니_상품_등록(로그인된_토큰, "1");
        ExtractableResponse<Response> response = 장바구니_상품_수량_수정(로그인된_토큰, "1", -1);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message"))
                        .isEqualTo("잘못된 형식입니다."),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(1100)
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 수정할 때 정상 케이스인 경우 200을 응답하고 수정된 상품을 알려준다.")
    @Test
    void putCartItemOk() {
        상품_등록(new ProductRequest("상품", 10000, "image.url"));
        장바구니_상품_등록(로그인된_토큰, "1");
        ExtractableResponse<Response> response = 장바구니_상품_수량_수정(로그인된_토큰, "1", 2);
        CartItemResponse cartItemResponse = response.jsonPath().getObject(".", CartItemResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("상품"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(10000),
                () -> assertThat(cartItemResponse.getImageUrl()).isEqualTo("image.url"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2)
        );

        ExtractableResponse<Response> response1 = 장바구니_상품_조회(로그인된_토큰);
        List<CartItemResponse> cartList = response1.jsonPath().getList("cartList", CartItemResponse.class);
        assertThat(cartList.get(0).getQuantity()).isEqualTo(2);
    }
}
