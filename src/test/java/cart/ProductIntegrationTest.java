package cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.entity.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql("delete.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("index 페이지를 조회한다")
    @Test
    public void indexTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("상품목록"));
    }

    @DisplayName("admin 페이지를 조회한다")
    @Test
    public void adminTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/admin")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("관리자 페이지"));
    }

    @DisplayName("상품을 등록한다")
    @Test
    public void createProducts() {
        ProductRequest productRequest = new ProductRequest("박스터", "https://boxster.com", 10000);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .post("/products")

                .then()

                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("박스터"))
                .body("imgUrl", equalTo("https://boxster.com"))
                .body("price", equalTo(10000));
        assertThat(productDao.findAll())
                .map(Product::getName)
                .containsExactly("박스터");
    }

    @DisplayName("상품을 수정한다")
    @Test
    public void updateProductTest() {
        Product gitchan = productDao.save(new Product("깃짱", "https://gitchan.com", 1000));
        ProductRequest productRequest = new ProductRequest("박스터", "https://boxster.com", 10000);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .put("/products/{경id}", gitchan.getId())

                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Product findProduct = productDao.findById(gitchan.getId()).get();
        assertAll(
                () -> assertThat(findProduct.getName()).isEqualTo("박스터"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(10000),
                () -> assertThat(findProduct.getImgUrl()).isEqualTo("https://boxster.com")
        );
    }

    @DisplayName("상품을 삭제한다")
    @Test
    public void deleteProductTest() {
        Product boxster = productDao.save(new Product("박스터", "1", 1000));
        RestAssured.given()

                .when()
                .delete("/products/{id}", boxster.getId())

                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        assertThat(productDao.findById(boxster.getId())).isEmpty();
    }
}
