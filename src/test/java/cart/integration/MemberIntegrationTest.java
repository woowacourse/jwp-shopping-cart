package cart.integration;

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

import static io.restassured.RestAssured.given;

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
    void addMember() {
        final MemberDto journey = new MemberDto(1L, "journey@gmail.com", "password", "져니", "010-1234-5678");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(journey)
                .post("/member")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
