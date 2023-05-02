package cart.integration;

import static io.restassured.RestAssured.given;

import cart.controller.dto.MemberDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("사용자 리스트를 조회한다.")
    void getMembers() {
        given()
            .when()
            .get("/settings")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("사용자 정보를 추가한다")
    @Sql("classpath:init.sql")
    void addMember() {
        final MemberDto journey = new MemberDto(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");

        given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(journey)
            .post("/member")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("단일 사용자 정보를 조회한다.")
    @Sql("classpath:init.sql")
    void getMember() {
        addMember();

        // when, then
        given()
            .when()
            .get("/member/{memberId}", 1L)
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }
}
