package cart.member.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    void 전체_사용자_조회(){
        given()
                .when()
                .log().all()
                .get("/settings")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);



    }
}