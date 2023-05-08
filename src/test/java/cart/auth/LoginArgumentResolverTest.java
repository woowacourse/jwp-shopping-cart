package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginArgumentResolverTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "홍길동";

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        jdbcTemplate.update("DELETE FROM member WHERE email = ?", EMAIL);
    }

    @Test
    @DisplayName("BaseLogin에서 해당 이메일과 패스워드의 멤버 객체를 반환한다")
    void loginArgumentResolverRightMember() {
        // given
        Long memberId = memberDao.insert(new Member(EMAIL, PASSWORD, NAME));
        TestMemberResponse expected = new TestMemberResponse(memberId, EMAIL, PASSWORD, NAME);

        // when then
        TestMemberResponse actual = RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/test")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TestMemberResponse.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("BaseLogin에서 해당 이메일과 패스워드의 멤버가 없으면 예외를 던진다")
    void loginArgumentResolverIncorrectMember() {
        // when then
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/test")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    @DisplayName("BaseLogin에서 헤더없이 요청시 예외를 던진다")
    void unAuthentication_throws() {
        // when then
        RestAssured.given().log().all()
                .when().get("/test")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
