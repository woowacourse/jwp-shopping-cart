package cart.integration;

import cart.dto.request.CartRequestDto;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @LocalServerPort
    private int port;

    final String email = "ditoo@wooteco.com";
    final String password = "ditoo1234";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("장바구니에 상품 추가 성공")
    @Sql("/product_dummy_data.sql")
    void create_success() {
        final CartRequestDto requestDto = new CartRequestDto(1);

        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/carts")
                .then().statusCode(HttpStatus.CREATED.value())
                .headers("Location", "/");
    }

    @Test
    @DisplayName("장바구니에 상품 추가 실패 - 잘못된 유저")
    @Sql("/product_dummy_data.sql")
    void create_fail_unauthorized() {
        final CartRequestDto requestDto = new CartRequestDto(1);

        RestAssured
                .given()
                .auth().preemptive().basic(email, password + "123")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/carts")
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("장바구니 조회 성공")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void read_success() {
        ExtractableResponse<Response> response = RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then()
                .extract();

        Configuration conf = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(conf).parse(response.asString());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat((int) documentContext.read("$.size()")).isEqualTo(2),
                () -> assertThat((String) documentContext.read("$[0]['name']")).isEqualTo("삼겹살"),
                () -> assertThat((String) documentContext.read("$[0]['image']")).isEqualTo("3-hierarchy-meat.jpg"),
                () -> assertThat((int) documentContext.read("$[0]['price']")).isEqualTo(16000),
                () -> assertThat((String) documentContext.read("$[1]['name']")).isEqualTo("목살"),
                () -> assertThat((String) documentContext.read("$[1]['image']")).isEqualTo("neck-meat.jpg"),
                () -> assertThat((int) documentContext.read("$[1]['price']")).isEqualTo(15000)
        );
    }

    @Test
    @DisplayName("장바구니 조회 실패 - 잘못된 유저")
    void read_fail_unauthorized() {
        RestAssured
                .given()
                .auth().preemptive().basic(email, password + 123)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("장바구니 상품 삭제 성공")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void delete_success() {
        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .when().delete("/carts/{id}")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 상품 삭제 실패 - 없는 상품")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void delete_fail_cartItem_not_found() {
        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 4)
                .when().delete("/carts/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("장바구니 상품 삭제 실패 - 잘못된 유저")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void delete_fail_unauthorized() {
        RestAssured
                .given()
                .auth().preemptive().basic(email, password + 123)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .when().delete("/carts/{id}")
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("장바구니 상품 삭제 실패 - 본인 것이 아닌 장바구니 상품")
    @Sql({"/product_dummy_data.sql", "/cart_dummy_data.sql"})
    void delete_fail_not_your_cartItem() {
        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 3)
                .when().delete("/carts/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }
}
