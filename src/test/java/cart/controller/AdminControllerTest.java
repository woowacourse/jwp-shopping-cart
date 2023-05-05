package cart.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cart.dto.ProductRequest;
import cart.service.JwpCartService;
import io.restassured.RestAssured;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwpCartService jwpCartService;

    static Stream<Arguments> makeDto() {
        return Stream.of(Arguments.arguments(new ProductRequest("a".repeat(256), "https://naver.com", 1000)),
            Arguments.arguments(new ProductRequest("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000)),
            Arguments.arguments(new ProductRequest("aaa", "https://naver.com", -1000)));
    }

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void index() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자 페이지를 조회한다.")
    void admin() {
        RestAssured.given().log().all()
            .accept(MediaType.TEXT_HTML_VALUE)
            .when().get("/admin")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        ProductRequest productRequest = new ProductRequest("리오", "http://asdf.asdf", 3000);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/admin/products")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @MethodSource("makeDto")
    @DisplayName("상품을 추가한다. - 잘못된 입력을 검증한다.")
    void addProductInvalidInput(ProductRequest productRequest) {

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/admin/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void updateProduct() {
        ProductRequest productRequest = new ProductRequest("리오", "http://asdf.asdf", 3000);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().put("/admin/products/1")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @MethodSource("makeDto")
    @DisplayName("상품 정보를 수정한다. - 잘못된 입력을 검증한다.")
    void updateProductInvalidInput(ProductRequest productRequest) {

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().put("/admin/products/1")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() {
        RestAssured.given().log().all()
            .when().delete("/admin/products/1")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }
}
