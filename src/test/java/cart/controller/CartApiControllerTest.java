package cart.controller;

import cart.dao.JdbcCartDao;
import cart.entity.Cart;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql({"classpath:truncateTable.sql","classpath:userTestData.sql"})
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
    void deleteTest(){
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com","password1")
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
