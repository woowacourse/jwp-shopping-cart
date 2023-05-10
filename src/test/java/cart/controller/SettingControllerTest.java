package cart.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SettingControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void 사용사_설정_페이지_연동_성공() {
        final String htmlBody = RestAssured.when()
                .get("/settings")
                .then()
                .contentType(ContentType.HTML)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

        assertThat(htmlBody).contains("<title>설정</title>");
    }
}
