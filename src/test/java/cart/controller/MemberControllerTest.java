package cart.controller;

import cart.dto.MemberRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    private MemberRequestDto memberRequestDto;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        memberRequestDto = new MemberRequestDto("eastsea@eastsea", "donghae");
    }

    @Test
    @DisplayName("멤버를 추가할 수 있다.")
    void createMember() {
        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequestDto)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
