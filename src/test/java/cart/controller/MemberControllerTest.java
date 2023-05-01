package cart.controller;

import cart.dao.member.MemberDao;
import cart.entity.MemberEntity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;

@Sql("classpath:test_init.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setup() {
        MemberEntity member1 = new MemberEntity("hello@naver.com", "hello", "01012345678", "qwer1234");
        MemberEntity member2 = new MemberEntity("good@naver.com", "good", "01012345678", "qwer1234");
        memberDao.save(member1);
        memberDao.save(member2);
    }

    @Test
    void add() {
        MemberEntity member3 = new MemberEntity("power@naver.com", "power", "01012345678", "qwer1234");

        RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(member3)
                .when()
                .post("http://localhost:" + port + "/member")
                .then()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("data.email", equalTo(member3.getEmail()));
    }

    @Test
    void edit() {
        MemberEntity member2 = new MemberEntity("good@naver.com", "power", "01012345678", "qwer1234");

        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(member2)
                .when()
                .put("http://localhost:" + port + "/member")
                .then()
                .contentType(ContentType.JSON)
                .body("data.name", equalTo(member2.getName()));
    }

    @Test
    void delete() {
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete("http://localhost:" + port + "/member/good@naver.com")
                .then()
                .contentType(ContentType.JSON)
                .body("status", equalTo(HttpStatus.OK.value()));

    }
}
