package cart;

import cart.dao.member.MemeberDao;
import cart.dao.product.ProductDao;
import cart.domain.Product;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    public static final String EMAIL = "abc@123";
    public static final String PASSWORD = "1234";

    @Autowired
    MemeberDao memeberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    Long productId;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from cart");
        jdbcTemplate.update("delete from product");
        jdbcTemplate.update("delete from member");
        memeberDao.save(EMAIL, PASSWORD);
        productId = productDao.save(new Product("이름", 1000, "url"));
    }

    @DisplayName("상품을 장바구니에 추가한다.")
    @Test
    void add() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품을 장바구니에서 제거한다.")
    @Test
    void delete() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원정보가 일치하지 않는 경우 401이 발생한다.")
    @ParameterizedTest
    @MethodSource("provideKeyAndValue")
    void add_exception(String email, String password) {
        RestAssured.given().log().all()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private static Stream<Arguments> provideKeyAndValue() {
        return Stream.of(
                Arguments.of(EMAIL + 1, PASSWORD),
                Arguments.of(EMAIL, PASSWORD + 1)
        );
    }


}
