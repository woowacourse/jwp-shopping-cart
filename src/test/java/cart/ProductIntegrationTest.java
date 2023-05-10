package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.Product;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/schema.sql")
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품이 추가되었을 때 CREATED 응답 코드를 반환한다")
    @Test
    void create() throws JSONException {
        JSONObject productAddRequest = parseJSON(Map.of(
                "name", "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십",
                "imageUrl", "a".repeat(1000),
                "price", 0
        ));

        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequest.toString())
                .when()
                .post("/products")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("상품이 추가 실패 때 BAD REQUEST 응답 코드를 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일"})
    void createFailName(String name) throws JSONException {
        JSONObject productAddRequest = parseJSON(Map.of(
                "name", name,
                "imageUrl", "url",
                "price", 1000
        ));

        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequest.toString())
                .when()
                .post("/products")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품이 추가 실패 때 BAD REQUEST 응답 코드를 반환한다")
    @Test
    void createFailUrl() throws JSONException {
        JSONObject productAddRequest = parseJSON(Map.of(
                "name", "name",
                "imageUrl", "a".repeat(1001),
                "price", 1000
        ));

        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequest.toString())
                .when()
                .post("/products")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품이 추가 실패 때 BAD REQUEST 응답 코드를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1_000_000_001})
    void createFailPrice(int price) throws JSONException {
        JSONObject productAddRequest = parseJSON(Map.of(
                "name", "name",
                "imageUrl", "url",
                "price", price
        ));

        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequest.toString())
                .when()
                .post("/products")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 수정 성공 시 NO CONTENT 응답 코드를 반환한다")
    @Test
    void update() throws JSONException {
        Long id = insertProduct();

        JSONObject productUpdateRequest = parseJSON(Map.of(
                "id", id,
                "name", "도이",
                "imageUrl", "doy.png",
                "price", 10000
        ));

        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest.toString())
                .when()
                .put("/products")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품 식제 성공 시 NO CONTENT 응답 코드를 반환한다")
    @Test
    void delete() {
        Long id = insertProduct();

        ExtractableResponse<Response> response = given()
                .when()
                .delete("/products/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private JSONObject parseJSON(Map<String, Object> parameters) throws JSONException {
        JSONObject parsed = new JSONObject();
        for (Entry<String, Object> entry : parameters.entrySet()) {
            parsed.put(entry.getKey(), entry.getValue());
        }

        return parsed;
    }

    private Long insertProduct() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(
                new Product("에밀", "emil.png", 1000)
        )).longValue();
    }
}
