package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

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

@DisplayName("장바구니 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartAcceptanceTest extends AcceptanceTest {

    private static final String CUSTOMER_ACCOUNT = "yeonlog";
    private static final String CUSTOMER_PASSWORD = "abAB12!!";

    @Test
    void 장바구니_추가() {
        // given
        Long productId = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 장바구니_추가(token, productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(LOCATION)).isNotBlank();
    }

    @Test
    void 존재하지_않는_상품으로_장바구니_추가() {
        // given
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 장바구니_추가(token, 100L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(findValue(response, "message")).contains("존재하지 않는 상품입니다.");
    }

    @Test
    void 회원_계정으로_장바구니_조회() {
        // given
        Long productId1 = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록_후_id_반환("맥주", 20_000, "http://example.com/beer.jpg");
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);

        장바구니_추가(token, productId1);
        장바구니_추가(token, productId2);

        // when
        ExtractableResponse<Response> response = 장바구니_조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(generateProductIdsInCart(response)).containsOnly(productId1, productId2);
    }

    private ExtractableResponse<Response> 장바구니_조회(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/cart")
                .then().log().all()
                .extract();
    }

    private List<Long> generateProductIdsInCart(ExtractableResponse<Response> response) {
        return response.jsonPath()
                .getList("cart", ProductResponse.class)
                .stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Test
    void 존재하지_않는_회원_계정으로_장바구니_조회() {
        // given
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);
        회원_탈퇴(token, CUSTOMER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 장바구니_조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(findValue(response, "message")).contains("존재하지 않는 사용자입니다.");
    }

    @Test
    void 장바구니_삭제() {
        // given
        String token = 회원_가입_후_토큰_발급(CUSTOMER_ACCOUNT, CUSTOMER_PASSWORD);
        Long productId = 상품_등록_후_id_반환("치킨", 10_000, "http://example.com/chicken.jpg");
        장바구니_추가(token, productId);

        Map<String, Object> request = new HashMap<>();
        request.put("productId", productId);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .body(request)
                .when().delete("/customers/cart")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
