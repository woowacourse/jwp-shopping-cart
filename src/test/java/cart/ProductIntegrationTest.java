package cart;

import cart.dao.ProductEntity;
import cart.dto.ProductDto;
import cart.service.ProductService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    ProductService productService;

    @Test
    public void getProducts() {

        productService.insert(new ProductDto("pizza", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 1000));
        productService.insert(new ProductDto("chicken", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 2000));

        List<ProductEntity> products = productService.findAll();

        var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(products)
                .when()
                .get("/")
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
