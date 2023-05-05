package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql({"classpath:truncateTable.sql","classpath:userTestData.sql"})
public class WebControllerTest {
    @LocalServerPort
    private int port;


    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
    @Test
    @DisplayName("인증 확인 테스트")
    void authorizationTest(){
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com","password1")
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
