package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;

@DisplayName("상품 관련 기능")
@Sql("/truncate.sql")
public class ProductAcceptanceTest extends AcceptanceTest {

    public static final String 치킨 = "치킨";
    public static final int PRICE1 = 10_000;
    public static final String IMAGE_URI1 = "http://example.com/chicken.jpg";

    public static final String 파자 = "파자";
    public static final int PRICE2 = 20_000;
    public static final String IMAGE_URI2 = "http://example.com/pizza.jpg";

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        //when //then
        final ExtractableResponse<Response> 제품_등록_응답 = 상품_등록_요청(치킨, PRICE1, IMAGE_URI1);

        assertThat(제품_등록_응답.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(제품_등록_응답.header("Location")).isNotBlank();
    }

    @DisplayName("전체 상품 목록을 조회한다")
    @Test
    void getProducts() {
        //given
        상품_등록_요청(치킨, PRICE1, IMAGE_URI1);
        상품_등록_요청(파자, PRICE2, IMAGE_URI2);

        //when
        final ExtractableResponse<Response> 제품_전체_조회 = 상품_목록_조회_요청();
        final ProductsResponse 제품_전체_조회_응답 = 제품_전체_조회.as(ProductsResponse.class);

        //then
        assertThat(제품_전체_조회.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(제품_전체_조회_응답.getTotalCount()).isEqualTo(2);
    }

    @DisplayName("페이지 쿼리 스트링이 있을 떄, 페이지에 맞게 전체 목록을 조회한다")
    @Test
    void getProductWithPage() {
        //given
        상품_등록_요청(치킨, PRICE1, IMAGE_URI1);
        상품_등록_요청(파자, PRICE2, IMAGE_URI2);
        상품_등록_요청("파스타", 2000, "url");
        상품_등록_요청("소세지",2000, "url");
        상품_등록_요청("김밥",2000, "url");
        상품_등록_요청("라면",2000, "url");
        상품_등록_요청("떡볶이",2000, "url");
        상품_등록_요청("오징어",2000, "url");
        상품_등록_요청("초밥",2000, "url");
        상품_등록_요청("캐밥",2000, "url");
        상품_등록_요청("스테이크",2000, "url");
        상품_등록_요청("옥수수",2000, "url");

        int page = 1;
        int size = 10;

        //when
        final ExtractableResponse<Response> 제품_전체_조회 = 상품_목록_조회_페이지_요청(page, size);
        final ProductsResponse 제품_전체_조회_응답 = 제품_전체_조회.as(ProductsResponse.class);

        //then
        assertThat(제품_전체_조회.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(제품_전체_조회_응답.getTotalCount()).isEqualTo(12);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        //given
        final Long 상품아이디 = 상품_등록되어_있음(치킨, PRICE1, IMAGE_URI1);

        //when
        final ExtractableResponse<Response> 제품_조회 = 상품_조회_요청(상품아이디);
        final ProductResponse 제품_조회_응답 = 제품_조회.as(ProductResponse.class);

        //then
        assertThat(제품_조회.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertAll(
                () -> assertThat(제품_조회_응답.getName()).isEqualTo(치킨),
                () -> assertThat(제품_조회_응답.getPrice()).isEqualTo(PRICE1),
                () -> assertThat(제품_조회_응답.getImageUrl()).isEqualTo(IMAGE_URI1)
        );
    }

    @DisplayName("{productId}에 해당하는 제품이 없을 경우, 상품을 조회할 수 없다.")
    @Test
    void getProductWithProductIDHasNoResource() {
        //given  // when
        final ExtractableResponse<Response> 제품_조회 = 상품_조회_요청(1L);
        final ExceptionResponse 제품_조회_예외 = 제품_조회.as(ExceptionResponse.class);

        //then
        assertThat(제품_조회.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(제품_조회_예외.getMessage()).isEqualTo("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        //given
        Long productId = 상품_등록되어_있음(치킨, PRICE1, IMAGE_URI1);

        // when
        final ExtractableResponse<Response> 상품_삭제_응답 = 상품_삭제_요청(productId);

        //then
        assertThat(상품_삭제_응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("{productId}에 해당하는 제품이 없을 경우, 상품을 삭제할 수 없다.")
    @Test
    void deleteProductWithProductIDHasNoResource() {
        // when
        final ExtractableResponse<Response> 상품_삭제_응답 = 상품_삭제_요청(1L);
        final ExceptionResponse 제품_조회_예외 = 상품_삭제_응답.as(ExceptionResponse.class);

        //then
        assertThat(상품_삭제_응답.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(제품_조회_예외.getMessage()).isEqualTo("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        ProductRequest productRequest = new ProductRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_페이지_요청(int page, int size) {
        return RestAssured
                .given().log().all()
                .queryParam("page", page)
                .queryParam("size", size)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }
}
