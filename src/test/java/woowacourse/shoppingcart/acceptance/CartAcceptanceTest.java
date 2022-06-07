package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_등록;
import static woowacourse.shoppingcart.acceptance.fixture.UserSimpleAssured.회원가입_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.fixture.UserSimpleAssured;
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

    private ExtractableResponse<Response> 장바구니_상품_등록(String token, String productId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .body(Map.of("productId", productId))
                .post("/users/me/carts")
                .then().log().all().extract();
    }
}
