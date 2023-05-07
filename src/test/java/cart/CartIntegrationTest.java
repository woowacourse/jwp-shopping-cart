package cart;

import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    private static final String EMAIL = "eunkeeeee@gmail.com";
    private static final String PASSWORD = "password1";
    private static final Member MEMBER_GITCHAN = new Member(EMAIL, PASSWORD);

    @Autowired
    private MemberDao memberDao;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void findItemsWhenNotLoggedInTest() {
        RestAssured.given()
                .log().all()

                .when()
                .get("/carts/all")

                .then()
                .log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void findItemsWhenLoggedInTest() {
        final Member savedMember = memberDao.save(MEMBER_GITCHAN);

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(savedMember.getEmail(), savedMember.getPassword())

                .when()
                .get("/carts/all")

                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void addItemWhenNotLoggedInTest() {
        RestAssured.given()
                .log().all()

                .when()
                .post("/carts/1")

                .then()
                .log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void addItemWhenLoggedInTest() {
        final Member savedMember = memberDao.save(MEMBER_GITCHAN);

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(savedMember.getEmail(), savedMember.getPassword())

                .when()
                .post("/carts/1")

                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void deleteItemWhenNotLoggedInTest() {
        RestAssured.given()
                .log().all()

                .when()
                .delete("/carts/1")

                .then()
                .log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    public void deleteItemWhenLoggedInTest() {
        final Member savedMember = memberDao.save(MEMBER_GITCHAN);

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(savedMember.getEmail(), savedMember.getPassword())

                .when()
                .delete("/carts/1")

                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
