package cart.controller;

import cart.service.AdminService;
import cart.service.MemberService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("http://localhost:8080/ 접속 시 메인 화면을 반환한다.")
    @Test
    void allProducts() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("메인 화면에서 물건 목록을 보여준다.")
    @Test
    void userAllProducts() {
        RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("관리자 화면을 반환한다.")
    @Test
    void adminAllProducts() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("설정 화면을 반환한다.")
    @Test
    void allUsers() {
        RestAssured.given().log().all()
                .when().get("/settings")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 화면을 반환한다.")
    @Test
    void getCartItem() {
        RestAssured.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
