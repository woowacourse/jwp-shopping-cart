package cart.controller;

import cart.dao.JdbcCartDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql({"classpath:truncateTable.sql", "classpath:userTestData.sql","classpath:productsTestData.sql","classpath:cartTestData.sql"})
public class CartApiControllerTest {
    @Autowired
    JdbcCartDao jdbcCartDao;
    @LocalServerPort
    private int port;


    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("장바구니 제거 테스트")
    void deleteTest() {
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com", "password1")
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("조회 테스트 테스트")
    void authorizationTest() {
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com", "password1")
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void insertCartTest() {
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com", "password")
                .when().post("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
