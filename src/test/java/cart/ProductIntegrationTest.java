package cart;

import static cart.dto.product.RequestFixture.NUNU_REQUEST;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.service.cart.CartCommandService;
import cart.service.product.ProductCommandService;
import cart.service.user.UserCommandService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SuppressWarnings({"SpellCheckingInspection", "NonAsciiCharacters"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
class ProductIntegrationTest {

    private static final Product 상품 = new Product("email", "password", 100);

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private CartCommandService cartCommandService;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @Test
    void getProducts() {
        final User 새_유저 = 회원가입();
        final Product 등록된_상품 = 상품_등록(상품);
        상품_카트에_등록_후_등록된_상품_id(새_유저, 등록된_상품);

        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void updateProduct() {
        //given
        final User 새_유저 = 회원가입();
        final Product 등록된_상품 = 상품_등록(상품);
        final long id = 상품_카트에_등록_후_등록된_상품_id(새_유저, 등록된_상품);

        //when
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(NUNU_REQUEST)
                .when()
                .put("/products/" + id)
                .then()
                .extract();

        //then
        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.body().jsonPath().getLong("productId")).isEqualTo(1),
                () -> assertThat(result.body().jsonPath().getString("name")).isEqualTo("누누"),
                () -> assertThat(result.body().jsonPath().getString("image")).isEqualTo("naver.com"),
                () -> assertThat(result.body().jsonPath().getInt("price")).isEqualTo(1)
        );
    }

    @Test
    void createProduct() {
        //when
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(NUNU_REQUEST)
                .when()
                .post("/products")
                .then()
                .extract();

        //then
        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(result.body().jsonPath().getLong("productId")).isPositive(),
                () -> assertThat(result.body().jsonPath().getString("name")).isEqualTo("누누"),
                () -> assertThat(result.body().jsonPath().getString("image")).isEqualTo("naver.com"),
                () -> assertThat(result.body().jsonPath().getInt("price")).isEqualTo(1)
        );
    }

    @Test
    void 카트에_없는_상품은_제거할_수_있음() {
        //given
        final Product 등록된_상품 = 상품_등록(상품);

        //when
        final var result = given()
                .when()
                .delete("/products/" + 등록된_상품.getProductId().getValue())
                .then()
                .extract();

        //then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 카트에_추가된_상품을_제거하면_예외() {
        //given
        final User 새_유저 = 회원가입();
        final Product 등록된_상품 = 상품_등록(상품);
        final long 등록된_상품_id = 상품_카트에_등록_후_등록된_상품_id(새_유저, 등록된_상품);

        //when
        final var result = given()
                .when()
                .delete("/products/" + 등록된_상품_id)
                .then()
                .extract();

        //then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private User 회원가입() {
        return userCommandService.save("asdf", "1234");
    }

    private long 상품_카트에_등록_후_등록된_상품_id(final User 가입자, final Product 등록_희망_상품) {
        final Cart cart = cartCommandService.addProduct(등록_희망_상품.getProductId().getValue(), 가입자.getEmail().getValue());
        return cart.getCartProducts().getCartProducts().get(0).getCartProductId().getValue();
    }

    private Product 상품_등록(final Product 새로운_상품) {
        return productCommandService.create(새로운_상품.getProductName(),
                새로운_상품.getProductImage().getValue(),
                새로운_상품.getProductPrice().getValue());
    }
}
