package cart.member;

import cart.member.dao.MemberDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;

import static cart.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MemberApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/members로 post를 보내면 생성된 자원의 URI를 반환한다")
    void post() throws JsonProcessingException {
        RestAssured.given().contentType(ContentType.JSON).body(toJson(NO_ID_MEMBER1)).log().all()
                   .when().post("/members")
                   .then().log().all().assertThat()
                   .statusCode(HttpStatus.SC_CREATED)
                   .header(HttpHeaders.LOCATION, "/members/1");

        assertThat(memberDao.findById(1).get().getEmail()).isEqualTo(NO_ID_MEMBER1.getEmail());
    }

    @Test
    @DisplayName("/settings로 get을 보내면 HTML page를 반환한다")
    void getMembers() throws JsonProcessingException {
        memberDao.save(NO_ID_MEMBER1);
        memberDao.save(NO_ID_MEMBER2);

        RestAssured.given().log().all()
                   .when().get("/settings")
                   .then().log().all().assertThat()
                   .statusCode(HttpStatus.SC_OK)
                   .contentType(ContentType.HTML);
    }
}
