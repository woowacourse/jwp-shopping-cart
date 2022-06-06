package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.LOCATION;
import static woowacourse.shoppingcart.acceptance.AcceptanceUtil.findValue;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.application.dto.ProductResponse;

@DisplayName("상품 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    void 상품_추가() {
        // given

        // when
        ExtractableResponse<Response> response = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(LOCATION)).isNotBlank();
    }

    @Test
    void 상품_목록_조회() {
        // given
        Long productId1 = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록_후_id_반환("맥주", 20_000, "http://example.com/beer.jpg");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(상품_id_목록(response)).contains(productId1, productId2);
    }

    private List<Long> 상품_id_목록(ExtractableResponse<Response> response) {
        return response.jsonPath()
                .getList("products", ProductResponse.class)
                .stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Test
    void 상품_id로_상품_조회() {
        // given
        Long id = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = id로_상품_조회(id);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Long findId = Long.valueOf(findValue(response, "id"));
        assertThat(findId).isEqualTo(id);
    }

    @Test
    void 존재하지_않는_상품_id로_상품_조회() {
        // given

        // when
        ExtractableResponse<Response> response = id로_상품_조회(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("존재하지 않는 상품입니다.");
    }

    @Test
    void 상품_삭제() {
        // given
        Long productId = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/{productId}", productId)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> id로_상품_조회(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products/{productId}", id)
                .then().log().all()
                .extract();
    }

    private Long 상품_등록_후_id_반환(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록(name, price, imageUrl);
        return Long.parseLong(response.header(LOCATION).split("/products/")[1]);
    }

    private ExtractableResponse<Response> 상품_등록(String name, int price, String imageUrl) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(상품_정보(name, price, imageUrl))
                .when().post("/products")
                .then().log().all()
                .extract();
    }

    private Map<String, Object> 상품_정보(String name, int price, String imageUrl) {
        Map<String, Object> request = new HashMap<>();
        request.put("name", name);
        request.put("price", price);
        request.put("imageUrl", imageUrl);
        return request;
    }
}
