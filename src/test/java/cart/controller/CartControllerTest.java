package cart.controller;

import static cart.fixture.MemberFixtures.*;
import static cart.fixture.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartDao cartDao;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("set FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.update("TRUNCATE TABLE CART");
        jdbcTemplate.update("TRUNCATE TABLE MEMBER");
        jdbcTemplate.update("TRUNCATE TABLE PRODUCT");
        jdbcTemplate.update("set FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    @DisplayName("GET /view-cart로 요청하면, cart.html을 렌더링한다.")
    void view_cart() {
        Response response = RestAssured.given().log().all()
                .get("/view-cart")
                .then().log().all()
                .extract().response();

        assertThat(response.getBody().asString()).contains("장바구니");
    }

    @Test
    @DisplayName("POST /cart/{productId}로 요청 시 헤더에 Authorization가 없으면 401을 반환한다.")
    void add_no_authorization_header_401() {
        // given
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        RestAssured.given().log().all()
                .post("/cart/" + insertedProductId)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("POST /cart/{productId}로 요청하면 201을 반환하고, 장바구니에 상품이 담긴다.")
    void add_201() {
        // given
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        Response response = RestAssured.given().log().all()
                .auth().preemptive().basic(DUMMY_EMAIL, DUMMY_PASSWORD)
                .post("/cart/" + insertedProductId)
                .then()
                .extract().response();

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getHeader("Location")).contains("/cart/")
        );
    }

    @Test
    @DisplayName("GET /cart/products로 요청 시 헤더에 Authorization가 없으면 401을 반환한다.")
    void findAll_no_authorization_header_401() {
        RestAssured.given().log().all()
                .get("/cart/products")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("GET /cart/products로 요청하면 200을 반환하고, 장바구니에 있는 상품 정보들을 모두 반환한다.")
    void findAll_200() {
        // given
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        RestAssured.given().log().all()
                .auth().preemptive().basic(DUMMY_EMAIL, DUMMY_PASSWORD)
                .post("/cart/" + insertedProductId);

        Response response = RestAssured.given().log().all()
                .auth().preemptive().basic(DUMMY_EMAIL, DUMMY_PASSWORD)
                .get("/cart/products")
                .then()
                .extract().response();
        JsonPath jsonPath = response.getBody().jsonPath();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getLong("[0].productId")).isEqualTo(insertedProductId),
                () -> assertThat(jsonPath.getString("[0].imgUrl")).isEqualTo(DUMMY_SEONGHA_IMG_URL),
                () -> assertThat(jsonPath.getString("[0].name")).isEqualTo(DUMMY_SEONGHA_NAME),
                () -> assertThat(jsonPath.getInt("[0].price")).isEqualTo(DUMMY_SEONGHA_PRICE)
        );
    }

    @Test
    @DisplayName("DELETE /cart/{productId}로 요청 시 헤더에 Authorization가 없으면 401을 반환한다.")
    void delete_no_authorization_header_401() {
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        RestAssured.given().log().all()
                .delete("/cart/" + insertedProductId)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("DELETE /cart/{productId}로 요청하면 204를 반환하고, 해당 멤버의 장바구니 상품을 제거한다.")
    void delete_204() {
        // given
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        RestAssured.given().log().all()
                .auth().preemptive().basic(DUMMY_EMAIL, DUMMY_PASSWORD)
                .post("/cart/" + insertedProductId);

        RestAssured.given().log().all()
                .auth().preemptive().basic(DUMMY_EMAIL, DUMMY_PASSWORD)
                .delete("/cart/" + insertedProductId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        List<ProductEntity> productEntities = cartDao.selectAllProductByMemberId(insertedMemberId);

        assertThat(productEntities).hasSize(0);
    }
}
