package woowacourse.shoppingcart.acceptance;

import static woowacourse.fixture.CartFixture.장바구니_삭제_요청;
import static woowacourse.fixture.CartFixture.장바구니_삭제_검증;
import static woowacourse.fixture.CartFixture.장바구니_아이템_목록_응답_검증;
import static woowacourse.fixture.CartFixture.장바구니_아이템_목록_조회_요청;
import static woowacourse.fixture.CartFixture.장바구니_아이템_목록_포함_검증;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_요청;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_ID_반환;
import static woowacourse.fixture.CartFixture.장바구니_아이템_추가_검증;
import static woowacourse.fixture.CustomFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomFixture.회원가입_요청;
import static woowacourse.fixture.ProductFixture.상품_등록되어_있음2;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        String token = getToken();

        productId1 = 상품_등록되어_있음2(token, "치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음2(token, "맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String token = getToken();

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, USER, productId1);

        장바구니_아이템_추가_검증(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String token = getToken();

        장바구니_아이템_추가_ID_반환(token, USER, productId1);
        장바구니_아이템_추가_ID_반환(token, USER, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token, USER);

        장바구니_아이템_목록_응답_검증(response);
        장바구니_아이템_목록_포함_검증(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String token = getToken();

        Long cartId = 장바구니_아이템_추가_ID_반환(token, USER, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, USER, cartId);

        장바구니_삭제_검증(response);
    }

    private String getToken() {
        회원가입_요청(
                new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        return token;
    }
}
