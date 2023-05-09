package cart.controller.api;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.CartProductResponse;
import cart.dto.CartProductsResponse;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "password";

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        jdbcTemplate.update("DELETE FROM member WHERE email = ?", EMAIL);
    }

    @Test
    @DisplayName("POST /api/cart/{productId}")
    void addProduct() {
        // given
        Long memberId = memberDao.insert(new Member(EMAIL, PASSWORD, "test"));
        Long productId = productDao.insert(new Product("피자", 1000, null));

        // when then
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/cart/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        Optional<Cart> cart = cartDao.findByMemberIdAndProductId(memberId, productId);
        assertThat(cart).isPresent();
    }

    @Test
    @DisplayName("GET /api/cart")
    void getCartProducts() {
        // given
        Long memberId = memberDao.insert(new Member(EMAIL, PASSWORD, "test"));
        List<Product> products = List.of(new Product((long) 1, "피자", 1000, "image1"),
                new Product((long) 2, "햄버거", 1000, "image2"));
        products.forEach(product -> {
            Long productId = productDao.insert(product);
            cartDao.insert(new Cart(memberId, productId));
        });

        // when then
        CartProductsResponse response = RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CartProductsResponse.class);

        assertThat(response.getProducts())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(products.stream()
                        .map(CartProductResponse::of)
                        .collect(Collectors.toList()));
    }

    @Test
    @DisplayName("DELETE /api/cart/{productId}")
    void deleteProductFromCart() {
        // given
        Long memberId = memberDao.insert(new Member(EMAIL, PASSWORD, "test"));
        Long productId = productDao.insert(new Product((long) 1, "피자", 1000, "image1"));
        cartDao.insert(new Cart(memberId, productId));

        // when then
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/cart/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Optional<Cart> cart = cartDao.findByMemberIdAndProductId(memberId, productId);
        assertThat(cart).isEmpty();
    }
}
