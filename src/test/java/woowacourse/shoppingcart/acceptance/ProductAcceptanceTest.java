package woowacourse.shoppingcart.acceptance;

import static woowacourse.fixture.CustomFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomFixture.회원가입_요청;
import static woowacourse.fixture.ProductFixture.상품_등록_요청;
import static woowacourse.fixture.ProductFixture.상품_등록되어_있음;
import static woowacourse.fixture.ProductFixture.상품_목록_조회_요청;
import static woowacourse.fixture.ProductFixture.상품_목록_검증;
import static woowacourse.fixture.ProductFixture.상품_삭제_요청;
import static woowacourse.fixture.ProductFixture.상품_삭제_검증;
import static woowacourse.fixture.ProductFixture.상품_조회_요청;
import static woowacourse.fixture.ProductFixture.상품_조회_검증;
import static woowacourse.fixture.ProductFixture.상품_추가_검증;
import static woowacourse.fixture.ProductFixture.조회_검증;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        String token = getToken();

        ExtractableResponse<Response> response = 상품_등록_요청(token, "치킨", 10_000, "http://example.com/chicken.jpg",
                20_000);

        상품_추가_검증(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        String token = getToken();

        Long productId1 = 상품_등록되어_있음(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        Long productId2 = 상품_등록되어_있음(token, "맥주", 20_000, "http://example.com/beer.jpg", 30_000);

        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        조회_검증(response);
        상품_목록_검증(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        String token = getToken();

        Long productId = 상품_등록되어_있음(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_검증(response);
        상품_조회_검증(response, productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        String token = getToken();

        Long productId = 상품_등록되어_있음(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);

        ExtractableResponse<Response> response = 상품_삭제_요청(token, productId);

        상품_삭제_검증(response);
    }

    private String getToken() {
        회원가입_요청(
                new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        return token;
    }
}
