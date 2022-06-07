package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.domain.Product;

import static woowacourse.acceptance.RestAssuredConvenienceMethod.*;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String URI = "/api/products";

    @DisplayName("상품 목록 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getProducts() {
        getRequestWithoutToken(URI)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품추가 - 성공한 경우 201 Created가 반환된다.")
    @Test
    void addProduct() {
        Product product = new Product("이름", 1000, "이미지주소");

        postRequestWithoutToken(product, URI)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품추가 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void addBad() {
        Product product = new Product("", 1000, "이미지주소");

        postRequestWithoutToken(product, URI)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getProduct() {
        getRequestWithoutToken(URI + "/1")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품조회 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void getBad() {
        getRequestWithoutToken(URI + "/99")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품삭제 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void deleteProduct() {
        deleteRequestWithoutToken(URI + "/1")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품삭제 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void deleteBad() {
        deleteRequestWithoutToken(URI + "/99")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
