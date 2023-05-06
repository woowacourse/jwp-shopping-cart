package shoppingbasket.cart.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shoppingbasket.cart.dao.CartDao;
import shoppingbasket.cart.dto.CartSelectResponseDto;
import shoppingbasket.cart.entity.CartEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @Autowired
    private CartDao cartDao;

    private static final String EMAIL = "mint@gmail.com";
    private static final String PASSWORD = "min567";
    private static final int MEMBER_ID = 2;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void insertTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", 1);

        final CartEntity response = RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(CartEntity.class);

        assertThat(response).isNotNull();
    }

    @Test
    void insertTest_exceptAuth_unauthorized() {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", 1);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
        final String responseBody = response.body().asString();

        assertThat(responseBody).isEqualTo("사용자가 선택되지 않았습니다.");
    }

    @Test
    void insertTest_passwordMismatch_unauthorized() {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", 1);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(EMAIL, "mismatchPassword")
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
        final String responseBody = response.body().asString();

        assertThat(responseBody).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void selectTest() {
        final CartEntity savedCart1 = cartDao.insert(MEMBER_ID, 1);
        final CartEntity savedCart2 = cartDao.insert(MEMBER_ID, 2);

        final CartSelectResponseDto[] responseArr = RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CartSelectResponseDto[].class);
        final List<CartSelectResponseDto> response = Arrays.asList(responseArr);

        assertThat(response).hasSize(2);
        assertThat(response.get(0).getId()).isEqualTo(savedCart1.getId());
        assertThat(response.get(1).getId()).isEqualTo(savedCart2.getId());
    }

    @Test
    void deleteTest() {
        final CartEntity savedCart = cartDao.insert(MEMBER_ID, 1);

        RestAssured
            .given().log().all()
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/cart/" + savedCart.getId())
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void getCartByIdTest() {
        final CartEntity savedCart = cartDao.insert(MEMBER_ID, 1);
        final Integer savedId = savedCart.getId();

        final CartSelectResponseDto response = RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/" + savedId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CartSelectResponseDto.class);

        assertThat(response.getId()).isEqualTo(savedId);
    }
}
