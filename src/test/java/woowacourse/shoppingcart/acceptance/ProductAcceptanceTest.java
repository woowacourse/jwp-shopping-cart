package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.RequestFixture.로그인_및_토큰_발급;
import static woowacourse.fixture.RequestFixture.로그인_후_상품_목록_조회_요청;
import static woowacourse.fixture.RequestFixture.로그인_후_상품_조회_요청;
import static woowacourse.fixture.RequestFixture.로그인하지_않고_상품_목록_조회_요청;
import static woowacourse.fixture.RequestFixture.로그인하지_않고_상품_조회_요청;
import static woowacourse.fixture.RequestFixture.상품_등록_요청;
import static woowacourse.fixture.RequestFixture.상품_등록되어_있음;
import static woowacourse.fixture.RequestFixture.상품_삭제_요청;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_추가_요청;
import static woowacourse.fixture.ResponseFixture.상품_삭제됨;
import static woowacourse.fixture.ResponseFixture.상품_추가됨;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    void 상품_추가() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @Test
    void 로그인하지_않고_상품_목록_조회() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        // when
        ExtractableResponse<Response> response = 로그인하지_않고_상품_목록_조회_요청();

        // then
        List<ProductResponse> productResponses = response.body().jsonPath()
                .getList("$", ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses.get(0).getId()).isEqualTo(productId1),
                () -> assertThat(productResponses.get(0).getCartId()).isNull(),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(0),
                () -> assertThat(productResponses.get(1).getId()).isEqualTo(productId2),
                () -> assertThat(productResponses.get(1).getCartId()).isNull(),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인_후_상품_목록_조회() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        String accessToken = 로그인_및_토큰_발급("puterism", "Shopping123!");

        장바구니_아이템_추가_요청(accessToken, productId1);

        // when
        ExtractableResponse<Response> response = 로그인_후_상품_목록_조회_요청(accessToken);

        // then
        List<ProductResponse> productResponses = response.body().jsonPath()
                .getList("$", ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses.get(0).getId()).isEqualTo(productId1),
                () -> assertThat(productResponses.get(0).getCartId()).isNotNull(),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(productResponses.get(1).getId()).isEqualTo(productId2),
                () -> assertThat(productResponses.get(1).getCartId()).isNull(),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인하지_않고_상품_조회() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = 로그인하지_않고_상품_조회_요청(productId);

        // then
        ProductResponse productResponse = response.as(ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponse.getId()).isEqualTo(productId),
                () -> assertThat(productResponse.getCartId()).isNull(),
                () -> assertThat(productResponse.getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인_후_상품_조회() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        String accessToken = 로그인_및_토큰_발급("puterism", "Shopping123!");

        장바구니_아이템_추가_요청(accessToken, productId);

        // when
        ExtractableResponse<Response> response = 로그인_후_상품_조회_요청(accessToken, productId);

        // then
        ProductResponse productResponse = response.as(ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponse.getId()).isEqualTo(productId),
                () -> assertThat(productResponse.getCartId()).isNotEqualTo(0L),
                () -> assertThat(productResponse.getQuantity()).isEqualTo(1)
        );
    }

    @Test
    void 존재하지_않는_상품_조회() {
        // when
        ExtractableResponse<Response> response = 로그인하지_않고_상품_조회_요청(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 상품_삭제() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        // then
        상품_삭제됨(response);
    }
}
