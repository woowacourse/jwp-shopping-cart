package cart.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.entity.CartEntity;
import cart.dto.entity.ProductEntity;
import io.restassured.RestAssured;
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
public class CartIntegrationTest {
    @LocalServerPort
    int port;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private int productId;
    private int memberId;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.execute("DELETE FROM cart");
        jdbcTemplate.execute("ALTER TABLE cart ALTER COLUMN id RESTART WITH 1");

        ProductDao productDao = new ProductDao(jdbcTemplate);
        ProductEntity productEntity = new ProductEntity(
                "치킨",
                "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg",
                10000
        );
        productId = productDao.save(productEntity);
        memberId = 1;
    }

    @DisplayName("장바구니 조회 테스트")
    @Test
    void Should_Success_When_Get() {
        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("장바구니에 상품이 없을 때 새롭게 저장하는 테스트")
    @Test
    void Should_Success_When_SaveNotExistProduct() {
        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/carts/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1));
    }

    @DisplayName("장바구니에 상품이 있을 때 개수 증가되는 테스트")
    @Test
    void Should_Success_When_SaveExistProduct() {
        cartDao.save(new CartEntity(memberId, productId));

        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/carts/1");

        assertThat(cartDao.findByProductId(productId).get(0).getCount()).isEqualTo(2);
    }

    @DisplayName("장바구니 상품 삭제 테스트")
    @Test
    void Should_Success_When_Delete() {
        cartDao.save(new CartEntity(memberId, productId));

        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/carts/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }
}
