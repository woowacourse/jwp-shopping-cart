package cart.controller;

import cart.dao.JdbcCartDao;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql({"classpath:truncateTable.sql", "classpath:userTestData.sql", "classpath:productsTestData.sql", "classpath:cartTestData.sql"})
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
        List<String> actual = RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com", "password1")
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath().getList("name");
        Assertions.assertThat(actual).containsExactly("test1", "test2");
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void insertCartTest() {
        RestAssured.given().log().all()
                .auth().preemptive().basic("test1@test1.com", "password1")
                .when().post("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "{0}가 틀릴 시 실패 테스트")
    @MethodSource("invalidProvider")
    void notValidUserTest(final String name, final String email, final String password, final String errorMessage) {
        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .when().post("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is(errorMessage));
    }

    private static Stream<Arguments> invalidProvider() {
        return Stream.of(
                Arguments.of("이메일", "wrong@email.com", "password", "해당 유저가 없습니다"),
                Arguments.of("패스워드", "test1@test1.com", "wrongPassword", "패스워드가 틀렸습니다")
        );
    }
}
