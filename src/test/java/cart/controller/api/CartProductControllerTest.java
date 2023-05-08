package cart.controller.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_TWO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartProductControllerTest extends ApiControllerTest {

    private static final String path = "/cart/products";

    @Test
    void 유저가_상품을_담으면_상태코드_201을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path + "/" + INITIAL_PRODUCT_ONE.getId())
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 유저의_상품_전체_목록을_조회하면_상태코드_200을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(1))
                .body("[0].id", is(INITIAL_PRODUCT_TWO.getId().intValue()))
                .body("[0].name", is(INITIAL_PRODUCT_TWO.getName()))
                .body("[0].image", is(INITIAL_PRODUCT_TWO.getImageUrl()))
                .body("[0].price", is(INITIAL_PRODUCT_TWO.getPrice()));
    }

    @Test
    void 유저가_장바구니_상품을_삭제할_경우_상태코드_204를_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path + "/" + INITIAL_PRODUCT_TWO.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 로그인_정보가_없는_경우_상태코드_403을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", is("로그인이 필요합니다"));
    }
}
