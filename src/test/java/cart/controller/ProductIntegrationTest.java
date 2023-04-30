package cart.controller;

import static cart.factory.ProductFactory.createProduct;
import static cart.factory.ProductRequestDtoFactory.createProductCreateRequest;
import static cart.factory.ProductRequestDtoFactory.createProductEditRequest;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductEditRequestDto;
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
    void after() {
        jdbcTemplate.execute("delete FROM product");
    }

    @Test
    @DisplayName("전체 상품을 조회한다.")
    public void find_products_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);

        // when & then
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();
        List<Product> responseProducts = response.jsonPath().getList("products", Product.class);
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
        ProductCreateRequestDto req = createProductCreateRequest();

        // when & then
        Response result = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post("/products");

        result.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);

        ProductEditRequestDto req = createProductEditRequest();

        // when
        Response result = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().put("/products/" + product.getId());

        // then
        result.then()
                .statusCode(HttpStatus.OK.value());

        Product editedProduct = productRepository.findById(product.getId()).get();

        assertAll(
                () -> assertThat(editedProduct.getName()).isEqualTo(req.getName()),
                () -> assertThat(editedProduct.getPrice()).isEqualTo(req.getPrice()),
                () -> assertThat(editedProduct.getImgUrl()).isEqualTo(req.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 제거한다.")
    void delete_product_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);
        List<Product> givenProducts = productRepository.findAll();

        // when
        Response result = given().log().all()
                .when().delete("/products/" + product.getId());

        // then
        result.then()
                .statusCode(HttpStatus.OK.value());

        List<Product> afterProducts = productRepository.findAll();
        assertThat(afterProducts.size() + 1).isEqualTo(givenProducts.size());
    }
}
