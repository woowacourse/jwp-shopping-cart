package cart.controller;

import cart.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("제품 등록 요청을 매핑하는 컨트롤러가 정상적으로 작동한다")
    void insert() throws JsonProcessingException {
        ProductDto productDto = new ProductDto("pizza", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 10000);
        String request = objectMapper.writeValueAsString(productDto);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("제품 수정 요청을 매핑하는 컨트롤러가 정상적으로 작동한다")
    void update() throws JsonProcessingException {

        int id = insertProduct("pizza", 10000, "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg");

        ProductDto modifiedProduct = new ProductDto("chicken", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 30000);
        String request = objectMapper.writeValueAsString(modifiedProduct);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .patch("/products/{id}", id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("제품 삭제 요청을 매핑하는 컨트롤러가 정상적으로 작동한다")
    void delete() {
        int id = insertProduct("pizza", 10000, "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg");

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/{id}", id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }

    private int insertProduct(final String name, final Integer price, final String image) {
        final String sql = "INSERT INTO PRODUCT (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(
                    sql, new String[]{"ID"}
            );
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, image);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}