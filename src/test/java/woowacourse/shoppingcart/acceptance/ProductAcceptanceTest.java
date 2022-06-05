package woowacourse.shoppingcart.acceptance;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        RestAssured
            .given(spec).log().all()
            .filter(document("query-products",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("products[]").description("상품 목록"),
                    fieldWithPath("products[].id").description("상품 식별 번호"),
                    fieldWithPath("products[].name").description("상품 이름"),
                    fieldWithPath("products[].price").description("상품 가격"),
                    fieldWithPath("products[].imageUrl").description("상품 이미지 URL")
                )
            ))
            .when().log().all()
            .get("/products")
            .then().log().all()
            .assertThat().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        RestAssured
            .given(spec).log().all()
            .filter(document("query-product",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("productId").description("상품 식별 번호")
                ),
                responseFields(
                    fieldWithPath("id").description("상품 식별 번호"),
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("price").description("상품 가격"),
                    fieldWithPath("imageUrl").description("상품 이미지 URL")
                )
            ))
            .when().log().all()
            .get("/products/{productId}", 1L)
            .then().log().all()
            .assertThat().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 상품 조회")
    @Test
    void getNotFoundProduct() {
        RestAssured
            .given().log().all()
            .when().log().all()
            .get("/products/{productId}", 100L)
            .then().log().all()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
