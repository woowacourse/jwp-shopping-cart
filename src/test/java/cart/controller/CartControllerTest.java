package cart.controller;

import cart.dao.CartJdbcDao;
import cart.dao.ProductJdbcDao;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CartControllerTest {

    private static final String EMAIL = "jeoninpyo726@gmail.com";
    private static final String PASSWORD = "a";

    @Autowired
    private CartJdbcDao cartJdbcDao;
    @Autowired
    private ProductJdbcDao productJdbcDao;


    @BeforeEach
    void setup(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/cart get요청에 200을 응답한다.")
    void cart() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().get("/cart/items")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/cart/{id} post오청 200을 응답한다.")
    void cartAddProduct() {
        Integer id = productJdbcDao.insert(new ProductEntity("비버", "a", 1000L));

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/cart/" + id)
                .then().log().all()
                .extract();

        List<CartEntity> cartEntities = cartJdbcDao.findByMemberId(Long.valueOf(id));

        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(cartEntities.get(0).getProductId()).isEqualTo(id)
        );
    }
}