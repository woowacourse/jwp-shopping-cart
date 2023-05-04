package cart.controller;

import static cart.factory.ProductFactory.createProduct;
import static cart.factory.ProductRequestDtoFactory.createProductCreateRequest;
import static cart.factory.ProductRequestDtoFactory.createProductEditRequest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductEditRequest;
import cart.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete FROM products");
    }

    @Test
    @DisplayName("전체 상품을 조회한다.")
    public void find_products_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();

        // then
        List<Product> responseProducts = response.jsonPath().getList("data.products", Product.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseProducts).asList().hasSize(1);
        assertAll(
                () -> assertThat(responseProducts.get(0).getName()).isEqualTo(product.getName()),
                () -> assertThat(responseProducts.get(0).getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(responseProducts.get(0).getImgUrl()).isEqualTo(product.getImgUrl())
        );

    }

    @Test
    @DisplayName("상품을 저장한다.")
    void create_product_success() {
        // given
        ProductCreateRequest req = createProductCreateRequest();

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post("/products").then().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("올바르지 않은 파라미터일 경우 400 응답을 반환한다.")
    void create_product_fail() {
        // given
        ProductCreateRequest req = new ProductCreateRequest("안녕", -10000, "imgimg");

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post("/products")
                .then().extract();
        // then
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);

        ProductEditRequest req = createProductEditRequest();

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().put("/products/" + product.getId()).then().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(200);

        Product editedProduct = productRepository.findById(product.getId()).get();

        assertAll(
                () -> assertThat(editedProduct.getName()).isEqualTo(req.getName()),
                () -> assertThat(editedProduct.getPrice()).isEqualTo(req.getPrice()),
                () -> assertThat(editedProduct.getImgUrl()).isEqualTo(req.getImgUrl())
        );
    }

    @Test
    @DisplayName("잘못된 인자를 사용하면 400 응답을 받는다.")
    void edit_product_fail() {
        // given
        Product product = createProduct();
        productRepository.add(product);

        ProductEditRequest req = new ProductEditRequest("수정", -100000, "imgimg");

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().put("/products/" + product.getId())
                .then().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("상품을 제거한다.")
    void delete_product_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);
        List<Product> givenProducts = productRepository.findAll();

        // when
        ExtractableResponse<Response> response =  given()
                .delete("/products/" + product.getId()).then().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(200);

        List<Product> afterProducts = productRepository.findAll();
        assertThat(afterProducts.size() + 1).isEqualTo(givenProducts.size());
    }
}
