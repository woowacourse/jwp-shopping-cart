package cart.controller;

import cart.service.AdminService;
import cart.service.MemberService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void clean() {
        adminService.deleteAll();
        memberService.deleteAllMembers();
    }

    @Test
    void allProducts() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void userAllProducts() {
        RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void adminAllProducts() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void allUsers() {
        RestAssured.given().log().all()
                .when().get("/settings")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getCartItem() {
        RestAssured.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
