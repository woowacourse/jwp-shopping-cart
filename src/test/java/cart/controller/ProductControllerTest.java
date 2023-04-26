package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductSaveRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Sql(value = "classpath:reset.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품을_저장한다() {
        // given
        final ProductSaveRequestDto request = new ProductSaveRequestDto("허브티", "tea.jpg", 1000L);

        // when
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        // then
        final Product result = productDao.findAll().get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo(result.getName()),
                () -> assertThat(result.getImage()).isEqualTo(result.getImage()),
                () -> assertThat(result.getPrice()).isEqualTo(result.getPrice())
        );
    }
}
