package cart.member.controller;

import cart.ApiControllerTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_MEMBER_TWO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberControllerTest extends ApiControllerTest {

    private static final String path = "/members";

    @Test
    void 모든_유저의_목록을_조회하면_상태코드_201을_반환한다() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("[0].id", is(INITIAL_MEMBER_ONE.getId().intValue()))
                .body("[0].email", is(INITIAL_MEMBER_ONE.getEmail()))
                .body("[0].password", is(INITIAL_MEMBER_ONE.getPassword()))
                .body("[1].id", is(INITIAL_MEMBER_TWO.getId().intValue()))
                .body("[1].email", is(INITIAL_MEMBER_TWO.getEmail()))
                .body("[1].password", is(INITIAL_MEMBER_TWO.getPassword()));
    }

    @Test
    void 한_명의_유저를_조회하면_상태코드_201을_반환한다() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path + "/" + INITIAL_MEMBER_TWO.getId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(INITIAL_MEMBER_TWO.getId().intValue()))
                .body("email", is(INITIAL_MEMBER_TWO.getEmail()))
                .body("password", is(INITIAL_MEMBER_TWO.getPassword()));
    }
}
