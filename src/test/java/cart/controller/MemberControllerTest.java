package cart.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.MemberDao;
import cart.dto.MemberRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private final MemberRegisterRequest memberRegisterRequest =
            new MemberRegisterRequest("SeongHa", "seongha@gmail.com", "1234");

    @Autowired
    private MemberDao memberDao;

    @Test
    @DisplayName("정상적인 요청으로 사용자 등록 API를 호출하면 사용자가 등록되고 200을 반환한다.")
    void register_201() {
        // when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRegisterRequest)
                .post("/member/register")
                .then()
                .log().all()
                .extract().response();

        // then
        // TODO : 사용자 목록 조회 기능이 구현되면 DAO에서 find해서 검증하기
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getHeader("Location")).isEqualTo("/member/settings")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임이 빈 값, 공백이면 사용자 등록 API를 호출하면 400을 반환한다.")
    void register_no_nickname_400(String nickname) {
        // given
        MemberRegisterRequest noNicknameMemberRequest
                = new MemberRegisterRequest(nickname, "seongha@gmail.com", "1234");

        // when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(noNicknameMemberRequest)
                .post("/member/register")
                .then()
                .log().all()
                .extract().response();

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.getBody().asString()).isEqualTo("닉네임은 필수입니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("이메일이 빈 값, 공백이면 사용자 등록 API를 호출하면 400을 반환한다.")
    void register_no_email_400(String email) {
        // given
        MemberRegisterRequest noEmailMemberRequest
                = new MemberRegisterRequest("SeongHa", email, "1234");

        // when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(noEmailMemberRequest)
                .post("/member/register")
                .then()
                .log().all()
                .extract().response();

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.getBody().asString()).isEqualTo("이메일은 필수입니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"seongha.gmail.com", "seongha@gmail", "@gmail.com"})
    @DisplayName("이메일이 이메일 형식이 아니면 사용자 등록 API를 호출하면 400을 반환한다.")
    void register_no_email_regex_400(String email) {
        // given
        MemberRegisterRequest noEmailMemberRequest
                = new MemberRegisterRequest("SeongHa", email, "1234");

        // when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(noEmailMemberRequest)
                .post("/member/register")
                .then()
                .log().all()
                .extract().response();

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.getBody().asString()).isEqualTo("이메일 형식에 맞지 않습니다. 이메일 형식 : aa@bb.cc")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호가 빈 값, 공백이면 사용자 등록 API를 호출하면 400을 반환한다.")
    void register_no_password_400(String password) {
        // given
        MemberRegisterRequest noPasswordMemberRequest
                = new MemberRegisterRequest("SeongHa", "seongha@gmail.com", password);

        // when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(noPasswordMemberRequest)
                .post("/member/register")
                .then()
                .log().all()
                .extract().response();

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.getBody().asString()).isEqualTo("비밀번호는 필수입니다.")
        );
    }
}
