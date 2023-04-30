package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerIntegratedTest {
    @LocalServerPort
    private int port;
    
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_index_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("상품목록"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_관리자_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("관리자 페이지"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
}
