package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.BAD_REQUEST;
import static woowacourse.Fixtures.예외메세지_검증;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProductsOfPage() {
        // given
        int page = 1;
        int limit = 2;

        // when
        ExtractableResponse<Response> response = 상품_목록_조회_요청(page, limit);

        // then
        조회_응답됨(response);
        상품_목록_포함됨(치킨.getId(), 피자.getId(), response);
    }

    @DisplayName("상품 개수가 1 이상이어야 한다.")
    @Test
    void getProductsOfPage_limitError() {
        // given
        int page = 2;
        int limit = 0;

        // when
        ExtractableResponse<Response> response = 상품_목록_조회_요청(page, limit);

        // then
        BAD_REQUEST(response);
        예외메세지_검증(response, "올바르지 않은 포맷의 페이지 입니다.");
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        Long 치킨Id = 치킨.getId();

        // when
        ExtractableResponse<Response> response = 상품_조회_요청(치킨Id);

        //then
        조회_응답됨(response);
        상품_조회됨(response, 치킨Id);
    }

    @DisplayName("해당 아이디의 상품이 없을 경우 조회할 수 없다.")
    @Test
    void getProduct_noIdError() {
        // given
        long productId = 1000L;

        // when
        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        //then
        BAD_REQUEST(response);
        예외메세지_검증(response, "올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(int page, int limit) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products?page=" + page + "&limit=" + limit)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductResponse resultProduct = response.as(ProductResponse.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }
}
