package cart.controller;

import cart.dto.MemberRequestDto;
import cart.dto.ProductSaveRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Base64;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
class CartsRestControllerTest {

    private MemberRequestDto memberRequestDto;
    private ProductSaveRequestDto productSaveRequestDto;
    private String encoded;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        memberRequestDto = new MemberRequestDto("eastsea@eastsea", "donghae");
        productSaveRequestDto = new ProductSaveRequestDto("포비", "https://techblog.woowahan.com/wp-content/uploads/img/2020-04-10/pobi.png", 10000);
        encoded = new String(Base64.getEncoder().encode("eastsea@eastsea:donghae".getBytes()));
    }

    @Test
    @DisplayName("카트에 상품을 추가한다.")
    void createCarts() {

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequestDto)
                .when().post("/member");


        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Basic " + encoded)
                .when().post("/carts/{id}", 1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("카트에 상품을 삭제한다.")
    void deleteCarts() {
        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequestDto)
                .when().post("/member");

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Basic " + encoded)
                .when().delete("/carts/{id}", 1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("카트에 상품을 조회한다.")
    void getCarts() {
        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequestDto)
                .when().post("/member");

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Basic " + encoded)
                .when().get("/carts")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
