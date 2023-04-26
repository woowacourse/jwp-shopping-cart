package cart.controller;

import static io.restassured.RestAssured.given;

import cart.service.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("전체 상품 조회 API 호출 시 전체 상품이 반환된다.")
    @Test
    void showAllProducts() {
        // post 몇 개 보내고 그 다음에 get으로 검증
        given().log().all()
                .when()
                .get("/admin")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품 등록 API 호출 시 상품을 등록한다.")
    @Test
    void registerProduct() {
        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("https://avatars.githubusercontent.com/u/95729738?v=4", "CuteSeonghaDoll",
                        25000))
                .when()
                .post("/admin/product")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품 정보 수정 API 호출 시 상품 정보가 수정된다.")
    @Test
    void modifyProduct() {
        String baseUrl = "/admin/product/";
        //given
        String redirectURI = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                        "CuteSeonghaDoll", 25000))
                .when()
                .post(baseUrl)
                .then()
                .extract().response().getHeader("Location");
        long savedId = Long.parseLong(redirectURI.replace(baseUrl, ""));

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("https://avatars.githubusercontent.com/u/70891072?v=4", "CuteBaronDoll", 2500))
                .when()
                .put("/admin/product/" + savedId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
        // TODO: 2023-04-26 검증 방법 생각
    }

    @DisplayName("상품 삭제 API 호출 시 상품이 삭제된다.")
    @Test
    void deleteProduct() {
        String baseUrl = "/admin/product/";
        //given
        String redirectURI = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                        "CuteSeonghaDoll", 25000))
                .when()
                .post(baseUrl)
                .then()
                .extract().response().getHeader("Location");
        long savedId = Long.parseLong(redirectURI.replace(baseUrl, ""));

        given().log().all()
                .when()
                .delete("/admin/product/" + savedId)
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}