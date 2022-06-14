package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredFixture;
import woowacourse.shoppingcart.dto.ProductRequest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        //given
        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");

        //when & then
        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());
    }

    @DisplayName("상품 전체 목록 조회 페이징 검증")
    @ParameterizedTest
    @CsvSource(value = {"12,1,12", "13,2,1", "24,2,12", "25,3,1", "26,3,2"})
    void getProductsByPaging(int allSize, int page, int expectSize) {
        //given
        for (int index = 0; index < allSize; index++) {
            RestAssuredFixture.post(new ProductRequest("치킨" + index, 10000, "http://example.com/chicken.jpg"), "/products", HttpStatus.CREATED.value());
        }

        //when & then
        RestAssuredFixture.getProducts("/products?size=12&page=" + page, HttpStatus.OK.value())
                .body("products.size()", is(expectSize));
    }

    @DisplayName("상품 전체 목록 조회 페이징 검증")
    @ParameterizedTest
    @CsvSource(value = {"13", "24", "20"})
    void getProductsByPaging2(int allSize) {
        //given
        for (int index = 0; index < allSize; index++) {
            RestAssuredFixture.post(new ProductRequest("치킨" + index, 10000, "http://example.com/chicken.jpg"), "/products", HttpStatus.CREATED.value());
        }

        //when & then
        RestAssuredFixture.getProducts("/products", HttpStatus.OK.value())
                .body("products.size()", is(allSize));
    }

    @DisplayName("상품 단건 조회")
    @Test
    void getProduct() {
        //given
        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("피자", 20000, "http://example.com/pizza.jpg");

        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());
        RestAssuredFixture.post(productRequest2, "/products", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.getProduct("/products/{productId}", HttpStatus.OK.value(), 1L)
                .body("name", is("치킨"))
                .body("price", is(10000))
                .body("imageUrl", is("http://example.com/chicken.jpg"));
    }

    @DisplayName("상품 전체 조회")
    @Test
    void getProduct2() {
        //given
        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("피자", 20000, "http://example.com/pizza.jpg");

        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());
        RestAssuredFixture.post(productRequest2, "/products", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.getProducts("/products", 200);
        //RestAssuredFixture.getProduct("/products", HttpStatus.OK.value(), 1L);
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        //given
        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");
        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.deleteProduct("/products/" + 1L, HttpStatus.NO_CONTENT.value());
    }
}
