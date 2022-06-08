package woowacourse.shoppingcart.acceptance;

import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.util.AcceptanceTestUtil.로그인을_하고_토큰을_받는다;
import static woowacourse.util.AcceptanceTestUtil.요청_실패함;
import static woowacourse.util.AcceptanceTestUtil.장바구니_아이템_목록_응답됨;
import static woowacourse.util.AcceptanceTestUtil.장바구니_아이템_목록_포함됨;
import static woowacourse.util.AcceptanceTestUtil.장바구니_아이템_수량_변경됨;
import static woowacourse.util.AcceptanceTestUtil.장바구니_아이템_수량을_변경한다;
import static woowacourse.util.AcceptanceTestUtil.장바구니_아이템_추가됨;
import static woowacourse.util.AcceptanceTestUtil.장바구니에_아이템을_추가한다;
import static woowacourse.util.AcceptanceTestUtil.장바구니에서_아이템을_삭제한다;
import static woowacourse.util.AcceptanceTestUtil.장바구니의_아이템_목록을_조회한다;
import static woowacourse.util.AcceptanceTestUtil.회원가입을_한다;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private String token;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "우테코");
        token = 로그인을_하고_토큰을_받는다("abc@woowahan.com", "1q2w3e4r!");

        productId1 = 상품_등록되어_있음("치킨", 10_000, 10, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, 10, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니에_아이템을_추가한다(token, productId1, 1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니에 상품 추가 요청을 할 때 상품 재고보다 많은 수량을 요청하면 상품을 추가할 수 없다.")
    @Test
    void addInvalidQuantity() {
        ExtractableResponse<Response> response = 장바구니에_아이템을_추가한다(token, productId1, 11);

        요청_실패함(response, "재고가 충분하지 않습니다.");
    }

    @DisplayName("장바구니에 상품을 추가하고 장바구니에 들어 있는 상품 목록을 조회한다.")
    @Test
    void showCart() {
        장바구니에_아이템을_추가한다(token, productId1, 1);
        장바구니에_아이템을_추가한다(token, productId2, 1);

        ExtractableResponse<Response> response = 장바구니의_아이템_목록을_조회한다(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니에 이미 들어 있는 상품에 대한 추가 요청을 하면 상품 수량을 증가시킨다.")
    @Test
    void addQuantity() {
        장바구니에_아이템을_추가한다(token, productId1, 1);
        장바구니에_아이템을_추가한다(token, productId1, 1);

        ExtractableResponse<Response> response = 장바구니의_아이템_목록을_조회한다(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_수량_변경됨(response, productId1, 2);
    }

    @DisplayName("장바구니에 들어 있는 상품의 수량을 업데이트하고 수량이 변경된 장바구니 정보를 받는다.")
    @Test
    void updateCartItem() {
        장바구니에_아이템을_추가한다(token, productId1, 1);

        ExtractableResponse<Response> response = 장바구니_아이템_수량을_변경한다(token, productId1, 5);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_수량_변경됨(response, productId1, 5);
    }

    @DisplayName("장바구니에 들어 있는 상품을 다시 장바구니에서 삭제하고 상품이 삭제된 장바구니 정보를 받는다.")
    @Test
    void deleteCartItem() {
        장바구니에_아이템을_추가한다(token, productId1, 1);
        장바구니에_아이템을_추가한다(token, productId2, 1);

        ExtractableResponse<Response> response = 장바구니에서_아이템을_삭제한다(token, productId1);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId2);
    }
}
