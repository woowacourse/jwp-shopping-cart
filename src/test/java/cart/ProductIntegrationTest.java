package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.product.dao.ProductDao;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import cart.product.dto.RequestProductDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @Autowired
    private ProductDao productDao;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }
    
    @Test
    @DisplayName("상품 리스트 get 테스트")
    public void getProducts() {
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    @DisplayName("상품 create 테스트")
    public void createProduct() {
        final RequestProductDto requestProductDto = new RequestProductDto("망고", "http://mango", 1000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .post("/products")
                .then()
                .extract();

        productDao.findAll().stream()
                .filter(it -> it.getName().getValue().equals("망고"))
                .findFirst()
                .orElseThrow();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    @DisplayName("상품 update 테스트")
    public void updateProduct() {
        final long id = 1L;
        if(productDao.findByID(id).isEmpty()) {
            productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        }

        final RequestProductDto requestProductDto = new RequestProductDto("에코", "http://echo", 2000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .put("/products/{id}", id)
                .then()
                .extract();

        final Product resultProduct = productDao.findByID(id).orElseThrow();
        assertThat(resultProduct.getName().getValue()).isEqualTo("에코");
        assertThat(resultProduct.getImage()).isEqualTo("http://echo");
        assertThat(resultProduct.getPrice().getValue()).isEqualTo(2000);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    @DisplayName("상품 delete 테스트")
    void deleteProduct() {
        final long id = 2L;
        if(productDao.findByID(id).isEmpty()) {
            productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        }

        final var deleteResult = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/{id}", id)
                .then()
                .extract();
        assertThat(productDao.findByID(id)).isEmpty();
        assertThat(deleteResult.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    @DisplayName("400 Bad Request 반환 테스트")
    void badRequestTest() {
        final RequestProductDto requestProductDto = new RequestProductDto("12345678900", "http://test", 1000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .post("/products")
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("404 Not Found 반환 테스트")
    void notFoundTest() {
        final Long maxId = productDao.findAll().stream()
                .map(Product::getId)
                .max(Long::compareTo)
                .orElse(0L);

        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/{id}", maxId + 1)
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
