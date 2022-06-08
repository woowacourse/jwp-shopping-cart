package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 전체 조회할 때,")
    @Nested
    class Describe_상품전체조회 {
        @DisplayName("상품이 10개 등록되어 있다면")
        @Nested
        class Context_상품10개등록 extends AcceptanceTest {
            @Test
            @DisplayName("상품 10개에 대한 정보들과 상태코드 200을 반환받는다.")
            void it_return_10_products() {
                // then
                ValidatableResponse response = RestAssured
                        .given().log().all()
                        .when().get("/products")
                        .then().log().all();

                response.statusCode(HttpStatus.OK.value());
            }
        }
    }

    @DisplayName("상품 1개를 상세 조회할 때,")
    @Nested
    class Describe_상품상세조회 {
        @DisplayName("ID가 1인 상품을 조회하면")
        @Nested
        class Context_Product_id_1 extends AcceptanceTest {
            @Test
            @DisplayName("ID가 1인 상품에 대한 정보와 상태코드 200을 반환받는다.")
                // then
            void it_return_productId_1() {
                ValidatableResponse response = RestAssured
                        .given().log().all()
                        .when().get("/products/1")
                        .then().log().all();

                response.statusCode(HttpStatus.OK.value());
            }
        }

        @DisplayName("존재하지 않는 ID인 상품을 조회하면")
        @Nested
        class Context_notExist_Product extends AcceptanceTest {
            @Test
            @DisplayName("NOT FOUND 상태코드를 반환받는다.")
            void it_return_notFound() {
                ValidatableResponse response = RestAssured
                        .given().log().all()
                        .when().get("/products/30")
                        .then().log().all();

                response.statusCode(HttpStatus.NOT_FOUND.value());
            }
        }
    }
}
