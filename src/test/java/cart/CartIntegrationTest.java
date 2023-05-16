package cart;

import cart.domain.CartEntity;
import cart.dto.AuthInfo;
import cart.dto.ProductDto;
import cart.service.CartService;
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
class CartIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Test
    void getCartItems() {

        productService.insert(new ProductDto("pizza", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 1000));
        productService.insert(new ProductDto("chicken", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 2000));

        String email = "roy@gmail.com";
        String password = "1234";

        AuthInfo authInfo = new AuthInfo(email, password);

        cartService.addItem(authInfo, 1);
        cartService.addItem(authInfo, 2);

        List<CartEntity> cartItems = cartService.searchItems(authInfo);

        var result = given()
                .auth().preemptive().basic(email, password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItems)
                .when().get("/cart/items")
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


}
