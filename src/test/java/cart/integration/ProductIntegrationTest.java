package cart.integration;

import cart.config.auth.Base64AuthInterceptor;
import cart.dto.ProductDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Base64Utils;

import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_EMAIL;
import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_NAME;
import static cart.config.auth.Base64AuthInterceptor.AUTHORIZATION_HEADER;
import static io.restassured.RestAssured.given;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    public static final String ADMIN = ADMIN_EMAIL + ":" + ADMIN_NAME;
    public static final String ADMIN_CREDENTIALS = Base64AuthInterceptor.BASIC + " " + Base64Utils.encodeToString(ADMIN.getBytes());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        String sql = "TRUNCATE TABLE PRODUCT";
        jdbcTemplate.execute(sql);
    }

    @Test
    void 상품을_조회한다() {
        given()
                .when().get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 상품을_추가한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 10000);

        given()
                .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_업데이트한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 100000);

        final ExtractableResponse<Response> response = given()
                .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        final String location = response.header("location");
        final String[] parsedLocation = location.split("/");
        final Long id = Long.parseLong(parsedLocation[parsedLocation.length - 1]);
        final ProductDto updatedProductDto = new ProductDto("코코닥", "https://github.com/", 10000);

        given()
                .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatedProductDto)
                .when().put("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 상품을_삭제한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 100000);

        final ExtractableResponse<Response> response = given()
                .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        final String location = response.header("location");
        final String[] parsedLocation = location.split("/");
        final Long id = Long.parseLong(parsedLocation[parsedLocation.length - 1]);

        given()
                .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
