package cart.controller;

import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.MemberDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static cart.Pixture.*;
import static org.hamcrest.core.IsEqual.equalTo;

@Sql({"classpath:test_init.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private final static String EMAIL = AUTH_MEMBER1.getEmail();
    private final static String PASSWORD = AUTH_MEMBER1.getPassword();
    private final static String INVALID_EMAIL = "invalid@email.com";
    private final static String INVALID_PASSWORD = "invalidPassword";
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CartDao cartDao;

    @BeforeEach
    void setUp(@LocalServerPort int port) {
        RestAssured.port = port;

        itemDao.save(CREATE_ITEM1);
        itemDao.save(CREATE_ITEM2);

        memberDao.save(AUTH_MEMBER1);

        cartDao.save(PUT_CART1);
    }

    @DisplayName("장바구니 상품 추가 테스트")
    @Test
    void addItemInCartTest_success() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/new/2")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(equalTo("ok"));
    }

    @DisplayName("이미 담은 상품을 다시 넣었을 때 장바구니에 상품을 담는 것은 실패한다.")
    @Test
    void addItemInCartTest_failInvalidContainsItemId() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/new/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", equalTo("이미 장바구니에 담은 상품입니다."));
    }

    @DisplayName("없는 상품 정보를 전달했을 때 장바구니에 상품을 담는 것은 실패한다.")
    @Test
    void addItemInCartTest_failInvalidNoItemId() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/new/3")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", equalTo("상품 정보를 다시 입력해주세요."));
    }

//    @DisplayName("잘못된 사용자 정보를 전달했을 때 장바구니에 상품을 담는 것은 실패한다.-이메일")
//    @Test
//    void addItemInCartTest_failInvalidEmail() {
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .auth().preemptive().basic(INVALID_EMAIL, PASSWORD)
//                .when().post("/carts/new/1")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("message", equalTo("사용자 정보가 올바르지 않습니다."));
//    }

//    @DisplayName("잘못된 사용자 정보를 전달했을 때 장바구니에 상품을 담는 것은 실패한다.-패스워드")
//    @Test
//    void addItemInCartTest_failInvalidPassword() {
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .auth().preemptive().basic(EMAIL, INVALID_PASSWORD)
//                .when().post("/carts/new/1")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("message", equalTo("사용자 정보가 올바르지 않습니다."));
//    }


    @Test
    @DisplayName("장바구니 상품 삭제 테스트")
    void deleteItemInCartTest() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/delete/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(equalTo("ok"));
    }

    @DisplayName("장바구니에 없는 상품 정보를 전달했을 때 장바구니에 상품을 빼는 것은 실패한다.")
    @Test
    void deleteItemInCartTest_failInvalidNoItemId() {
        String message = "상품 정보를 다시 입력해주세요.";

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/delete/3")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", equalTo(message));
    }

//    @DisplayName("잘못된 사용자 정보를 전달했을 때 장바구니에 상품을 담는 것은 실패한다.-이메일")
//    @Test
//    void deleteItemInCartTest_failInvalidEmail() {
//        String message = "사용자 정보가 올바르지 않습니다.";
//
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .auth().preemptive().basic(INVALID_EMAIL, PASSWORD)
//                .when().post("/carts/delete/1")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("message", equalTo(message));
//    }

//    @DisplayName("잘못된 사용자 정보를 전달했을 때 장바구니에 상품을 담는 것은 실패한다.-패스워드")
//    @Test
//    void deleteItemInCartTest_failInvalidPassword() {
//        String message = "사용자 정보가 올바르지 않습니다.";
//
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .auth().preemptive().basic(EMAIL, INVALID_PASSWORD)
//                .when().post("/carts/delete/1")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("message", equalTo(message));
//    }

}
