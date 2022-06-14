package woowacourse.shoppingcart.acceptance;

import static woowacourse.fixture.CustomerFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomerFixture.회원가입_요청;
import static woowacourse.fixture.ProductFixture.상품_등록_요청;
import static woowacourse.fixture.ProductFixture.상품_등록후_ID반환;
import static woowacourse.fixture.ProductFixture.상품_목록_검증;
import static woowacourse.fixture.ProductFixture.상품_목록_조회_요청;
import static woowacourse.fixture.ProductFixture.상품_삭제_검증;
import static woowacourse.fixture.ProductFixture.상품_삭제_요청;
import static woowacourse.fixture.ProductFixture.상품_조회_검증;
import static woowacourse.fixture.ProductFixture.상품_조회_요청;
import static woowacourse.fixture.ProductFixture.상품_추가_검증;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.domain.Product;
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

        Product product1 = new Product("치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        Product product2 = new Product("맥주", 20_000, "http://example.com/beer.jpg", 30_000);
        Long productId1 = 상품_등록후_ID반환(token, product1);
        Long productId2 = 상품_등록후_ID반환(token, product2);

        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        상품_목록_검증(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        String token = getToken();

        Product product = new Product("치킨", 10_000, "http://example.com/chicken.jpg", 20_000L);
        Long productId = 상품_등록후_ID반환(token, product);

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        상품_조회_검증(response, productId, product);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        String token = getToken();

        Product product = new Product("치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        Long productId = 상품_등록후_ID반환(token, product);

        ExtractableResponse<Response> response = 상품_삭제_요청(token, productId);

        상품_삭제_검증(response);
    }

    private String getToken() {
        회원가입_요청(
                new CustomerCreateRequest("philz@gmail.com", "swcho", "1q2w3e4r!"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "1q2w3e4r!"));
        return token;
    }
}
