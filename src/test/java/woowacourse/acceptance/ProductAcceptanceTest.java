package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
@SuppressWarnings("NonAsciiCharacters")
@SpringBootAcceptanceTest
public class ProductAcceptanceTest {

    private Long productId1;
    private Long productId2;

    @Autowired
    private ProductInsertUtil productInsertUtil;

    @BeforeEach
    void init() {
        productId1 = productInsertUtil.insert("치킨", 10000, "https://example.com/chicken.jpg");
        productId2 = productInsertUtil.insert("맥주", 20000, "https://example.com/beer.jpg");
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        ExtractableResponse<Response> response = 상품_조회_요청(productId1);

        조회_응답됨(response);
        상품_조회됨(response, productId1);
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/products")
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

