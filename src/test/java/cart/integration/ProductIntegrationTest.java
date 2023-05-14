package cart.integration;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.service.dto.product.ProductAddRequest;
import cart.service.dto.product.ProductModifyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@Sql("/schema.sql")
@Sql("/truncate.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductEntity 상품 = new ProductEntity(
            "피자",
            100000,
            "https://img.freepik.com/free-photo/top-view-of-pepperoni-pizza-with-mushroom-sausages-bell-pepper-olive-and-corn-on-black-wooden_141793-2158.jpg"
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void 상품_정보를_입력_받아_상품을_등록한다() {
        ProductAddRequest 요청 = 상품_등록_요청_생성(상품.getName(), 상품.getPrice(), 상품.getName());

        ExtractableResponse<Response> response = 상품_등록_요청(요청);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/products/1")
        );
    }

    private ExtractableResponse<Response> 상품_등록_요청(final ProductAddRequest 요청) {
        return given()
                .contentType(JSON)
                .body(toJson(요청))
                .when().post("/products")
                .then()
                .log().all()
                .extract();
    }

    private ProductAddRequest 상품_등록_요청_생성(final String 이름, final int 가격, final String 이미지_URL) {
        return new ProductAddRequest(이름, 가격, 이미지_URL);
    }

    @Test
    public void 수정된_정보를_입력_받아_상품을_수정한다() {
        Long 상품_ID = 상품을_등록한다(상품);

        ProductModifyRequest 수정_요청 = 상품_수정_요청_생성("치킨", 상품.getPrice(), 상품.getImageUrl());
        ExtractableResponse<Response> response = 상품_수정_요청(상품_ID, 수정_요청);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 상품_수정_요청(final Long 상품_ID, final ProductModifyRequest 요청) {
        return given()
                .contentType(JSON)
                .body(toJson(요청))
                .when().put("/products/" + 상품_ID)
                .then()
                .log().all()
                .extract();
    }

    private ProductModifyRequest 상품_수정_요청_생성(final String 이름, final int 가격, final String 이미지_URL) {
        return new ProductModifyRequest(이름, 가격, 이미지_URL);
    }

    @Test
    public void 상품을_삭제한다() {
        final Long 상품_ID = 상품을_등록한다(상품);

        ExtractableResponse<Response> response = 상품_삭제_요청(상품_ID);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 상품_삭제_요청(final Long 상품_ID) {
        return given()
                .contentType(JSON)
                .when().delete("/products/" + 상품_ID)
                .then()
                .log().all()
                .extract();
    }

    @Test
    public void 없는_상품_ID로_수정시_예외가_발생한다() {
        상품을_등록한다(상품);
        final Long 없는_상품_ID = 2L;
        ProductModifyRequest 수정_요청 = 상품_수정_요청_생성("치킨", 1000, "새로운이미지");

        ExtractableResponse<Response> response = 상품_수정_요청(없는_상품_ID, 수정_요청);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void 없는_상품_ID로_삭제시_예외가_발생한다() {
        상품을_등록한다(상품);
        final Long 없는_상품_ID = 2L;

        ExtractableResponse<Response> response = 상품_삭제_요청(없는_상품_ID);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    private Long 상품을_등록한다(final ProductEntity 상품) {
        return productDao.save(상품);
    }

    private String toJson(final ProductAddRequest productAddRequest) {
        try {
            return objectMapper.writeValueAsString(productAddRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJson(final ProductModifyRequest productModifyRequest) {
        try {
            return objectMapper.writeValueAsString(productModifyRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
