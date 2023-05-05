package cart.controller;

import cart.dao.MemberDao;
import cart.dto.MemberRequest;
import cart.dto.MemberResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerIntegratedTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        RestAssured.port = port;
        memberDao.deleteAll();
    }

    @Test
    void 사용자_목록을_조회한다() {
        memberDao.save(FIRST_MEMBER.MEMBER);

        List<MemberResponse> memberResponses = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/members")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList("", MemberResponse.class);

        MemberResponse memberResponse = memberResponses.get(0);
        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(FIRST_MEMBER.MEMBER_WITH_ID);
    }

    @Test
    void 사용자를_생성한다() {
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(FIRST_MEMBER.REQUEST)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/members/1");
    }

    @ParameterizedTest(name = "{displayName} : email = {0}")
    @NullAndEmptySource
    void 이메일이_null_또는_empty일_시_예외_발생(final String email) {
        final MemberRequest memberRequest = new MemberRequest(email, "password");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이메일을 입력해주세요."));
    }

    @Test
    void 이메일_형식이_아니면_예외_발생() {
        final MemberRequest memberRequest = new MemberRequest("abcd", "password");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이메일 형식으로 입력해주세요."));
    }

    @Test
    void 이메일_길이가_30초과일때_예외_발생() {
        String textOver30 = "a".repeat(31);
        final MemberRequest memberRequest = new MemberRequest(textOver30 + "@test.com", "password");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이메일은 30자까지 입력가능합니다."));
    }

    @Test
    void 비밀번호_길이가_30초과일때_예외_발생() {
        String textOver30 = "a".repeat(31);
        final MemberRequest memberRequest = new MemberRequest("email@test.com", textOver30);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 비밀번호는 5자 이상, 30자이하까지 입력가능합니다."));
    }

    @Test
    void 비밀번호_길이가_5미만일때_예외_발생() {
        final MemberRequest memberRequest = new MemberRequest("email@test.com", "1234");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 비밀번호는 5자 이상, 30자이하까지 입력가능합니다."));
    }
}
