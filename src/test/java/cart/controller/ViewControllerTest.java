package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerTest {

    @LocalServerPort
    int port;

    @Nested
    @DisplayName("상품 목록 페이지를 조회하는 home 메서드 테스트")
    class homeTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 조회가 되었다면 상태코드 200을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .when().get("/")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    @DisplayName("관리자 페이지를 조회하는 admin 메서드 테스트")
    class adminTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 조회가 되었다면 상태코드 200을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .when().get("/admin")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    @DisplayName("설정 페이지를 조회하는 settings 메서드 테스트")
    class settingsTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 조회가 되었다면 상태코드 200을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .when().get("/settings")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }

}
