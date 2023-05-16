package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("장바구니에 등록된 상품을 조회한다.")
    void itemList() {
        String email = "roy@gmail.com";
        String password = "1234";

        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addItem() {
        String email = "roy@gmail.com";
        String password = "1234";

        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/items/3")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("존재하지 않는 사용자의 장바구니에 상품을 추가하면 예외가 발생한다.")
    void addItemNonExistMember() {
        String email = "NonExistMember";
        String password = "1234";

        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/items/10")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 사용자 email입니다."));
    }


    @Test
    @DisplayName("장바구니의 상품을 삭제한다.")
    void removeItem() {
        String email = "roy@gmail.com";
        String password = "1234";

        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
    
}