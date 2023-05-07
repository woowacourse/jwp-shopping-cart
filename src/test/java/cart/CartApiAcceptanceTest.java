package cart;

import cart.controller.dto.CartProductsRequest;
import cart.entity.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CartApiAcceptanceTest {

    private static final RowMapper<Product> productRowMapper = (rs, rowNum) ->
            new Product(rs.getLong("id"),
                    rs.getString("product_name"),
                    rs.getInt("product_price"),
                    rs.getString("product_image"));

    private static final String SUCCESS_CREDENTIAL = Base64.getEncoder().encodeToString("test@email.com:12345".getBytes());
    private static final String WRONG_PASSWORD_CREDENTIAL = Base64.getEncoder().encodeToString("test@email.com:12349".getBytes());
    private static final String WRONG_ID_CREDENTIAL = Base64.getEncoder().encodeToString("wrong@email.com:12345".getBytes());

    @LocalServerPort
    private int port;

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertCart;

    @Autowired
    public CartApiAcceptanceTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsertCart = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_added_product")
                .usingGeneratedKeyColumns("id");
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @ParameterizedTest(name = "인증 실패 테스트 - {1}")
    @MethodSource("provideWrongCredentials")
    void unauthorized_test(final String basicAuthCredential, final String cause) {
        RestAssured.given().log().all()
                .header("Authorization", "Basic " + basicAuthCredential)
                .when().get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private static Stream<Arguments> provideWrongCredentials() {
        return Stream.of(
                Arguments.arguments(WRONG_PASSWORD_CREDENTIAL, "잘못된 비밀번호"),
                Arguments.arguments(WRONG_ID_CREDENTIAL, "등록되지 않은 사용자")
        );
    }

    @Test
    @DisplayName("카트에 추가 테스트")
    @Transactional
    void add_to_cart_test() {
        final Product productToAdd = findFirstProduct();

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + SUCCESS_CREDENTIAL)
                .contentType(ContentType.JSON)
                .body(new CartProductsRequest(productToAdd.getId()))
                .when().post("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    private Product findFirstProduct() {
        return jdbcTemplate.queryForObject("SELECT * FROM  products LIMIT 1;", productRowMapper);
    }

    @Test
    @DisplayName("사용자 상품 검색 테스트")
    void get_cart_product_test() {
        final Product product = findFirstProduct();
        final String email = "test@email.com";
        insertToCartTable(email, product.getId());

        final Integer totalCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM cart_added_product " +
                        "WHERE user_email = ?"
                , Integer.class, email);

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + SUCCESS_CREDENTIAL)
                .when().get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(totalCount));
    }

    private long insertToCartTable(final String email, final long productId) {
        final MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("user_email", email)
                .addValue("product_id", productId);

        return simpleJdbcInsertCart.executeAndReturnKey(source).longValue();
    }

    @Test
    @DisplayName("카트에서 상품 제거 테스트")
    void delete_test() {
        final Product product = findFirstProduct();
        final long id = insertToCartTable("test@email.com", product.getId());

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + SUCCESS_CREDENTIAL)
                .when().delete("/cart/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("없는 카트 id접근시 실패 테스트")
    void find_by_id_fail_test() {
        final Product product = findFirstProduct();
        final long id = insertToCartTable("test@email.com", product.getId()) + 1L;

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + SUCCESS_CREDENTIAL)
                .when().delete("/cart/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("다른 사용자 카트 상품 제거요청시 forbidden 테스트")
    void delete_forbidden_test() {
        final Product product = findFirstProduct();
        final long id = insertToCartTable("test2@email.com", product.getId());

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + SUCCESS_CREDENTIAL)
                .when().delete("/cart/" + id)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
