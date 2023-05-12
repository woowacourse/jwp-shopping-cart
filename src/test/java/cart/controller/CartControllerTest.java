package cart.controller;

import static org.hamcrest.Matchers.equalTo;

import cart.dao.CartItemDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class CartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("인증된 특정 사용자에 대한 카트 목록을 성공적으로 반환한다.")
    @Test
    void getCartItem() {
        RestAssured.given().log().all()
                .auth().preemptive().basic("dino96@naver.com", "jjongwa96")
                .when().get("/cart/item")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("케로로"))
                .body("[0].price", equalTo(10000))
                .body("[0].image", equalTo("https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp"))
                .body("[1].id", equalTo(2))
                .body("[1].name", equalTo("기로로"))
                .body("[1].price", equalTo(20000))
                .body("[1].image", equalTo("https://i.namu.wiki/i/DPaC8VuH9-jXjF5Ol4C9HM6T7Dy1ak-7fKpXbMEJUgdMr_YIlZmBbLaEFtenyHhUJiLaNPSorIv7Ly6_9WXRDsNJwJK06xXoy8_jJpf5Kd7e8eIm9N_kLMS5VBqORgAfBVJW6gepvdK7kZkVYZGJ3A.webp"))
                .extract();
    }

    @DisplayName("인증된 특정 사용자의 카트에 물건을 추가한다.")
    @Test
    void insertCartItem() {
        final int productId = 3;

        RestAssured.given().log().all()
                .auth().preemptive().basic("dino96@naver.com", "jjongwa96")
                .when().post("/cart/item?productId=" + productId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("인증된 특정 사용자의 카트에 물건을 삭제한다.")
    @Test
    void deleteCartItem() {
        final int cartId = 3;

        RestAssured.given().log().all()
                .auth().preemptive().basic("dino96@naver.com", "jjongwa96")
                .when().delete("/cart/item?cartId=" + cartId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
