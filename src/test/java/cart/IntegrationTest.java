package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.cart.CartDao;
import cart.dao.cart.JdbcCartDao;
import cart.dao.member.JdbcMemberDao;
import cart.dao.member.MemberDao;
import cart.dao.product.JdbcProductDao;
import cart.dao.product.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.dto.cart.RequestCartDto;
import cart.dto.product.RequestProductDto;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;
    private MemberDao memberDao;
    private CartDao cartDao;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
        productDao = new JdbcProductDao(jdbcTemplate);
        memberDao = new JdbcMemberDao(jdbcTemplate);
        cartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품 리스트 get 테스트")
    public void getProducts() {
        final long id1 = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        final long id2 = productDao.insert(new Product(new Name("디디"), "http://dd", new Price(2000)));

        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();

        final List<Product> products = productDao.findAll();
        assertThat(products.size()).isEqualTo(2);
        assertThat(productDao.findByID(id1).orElseThrow().getName().getValue()).isEqualTo("망고");
        assertThat(productDao.findByID(id2).orElseThrow().getName().getValue()).isEqualTo("디디");
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

        final Product product = productDao.findAll().stream()
                .filter(it -> it.getName().getValue().equals("망고"))
                .findFirst()
                .orElseThrow();
        assertThat(product.getName().getValue()).isEqualTo("망고");
        assertThat(product.getImage()).isEqualTo("http://mango");
        assertThat(product.getPrice().getValue()).isEqualTo(1000);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    @DisplayName("상품 update 테스트")
    public void updateProduct() {
        final long id = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));

        final RequestProductDto requestProductDto = new RequestProductDto("망고", "http://updated", 2000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .put("/products/{id}", id)
                .then()
                .extract();

        final Product resultProduct = productDao.findByID(id).orElseThrow();
        assertThat(resultProduct.getName().getValue()).isEqualTo("망고");
        assertThat(resultProduct.getImage()).isEqualTo("http://updated");
        assertThat(resultProduct.getPrice().getValue()).isEqualTo(2000);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    @DisplayName("상품 delete 테스트")
    void deleteProduct() {
        final long id = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));

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
    @DisplayName("장바구니 리스트 get 테스트")
    void getCarts() {
        final String email = "mango@wooteco.com";
        final String password = "mangopassword";
        final Long memberId = memberDao.findByEmailAndPassword(email, password).orElseThrow().getId();
        final Long productId = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        cartDao.insert(new Cart(memberId, productId));

        final var result = given()
                .auth().preemptive().basic(email, password)
                .when()
                .get("/carts")
                .then()
                .extract();

        final Cart cart = cartDao.findByMemberIdAndProductId(memberId, productId).orElseThrow();
        assertThat(cart.getMemberId()).isEqualTo(memberId);
        assertThat(cart.getProductId()).isEqualTo(productId);
        assertThat(cart.getQuantity().getValue()).isEqualTo(1);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 create 테스트")
    void createCart() {
        final String email = "mango@wooteco.com";
        final String password = "mangopassword";
        final Long productId = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        final RequestCartDto requestCartDto = new RequestCartDto(productId);

        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(email, password)
                .body(requestCartDto)
                .when()
                .post("/carts")
                .then()
                .extract();

        final Long memberId = memberDao.findByEmailAndPassword(email, password).orElseThrow().getId();
        final Cart cart = cartDao.findByMemberIdAndProductId(memberId, productId).orElseThrow();
        assertThat(cart.getMemberId()).isEqualTo(memberId);
        assertThat(cart.getProductId()).isEqualTo(productId);
        assertThat(cart.getQuantity().getValue()).isEqualTo(1);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("장바구니 delete 테스트")
    void deleteCart() {
        final String email = "mango@wooteco.com";
        final String password = "mangopassword";
        final Long memberId = memberDao.findByEmailAndPassword(email, password).orElseThrow().getId();
        final Long productId = productDao.insert(new Product(new Name("망고"), "http://mango", new Price(1000)));
        cartDao.insert(new Cart(memberId, productId));

        final var deleteResult = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(email, password)
                .when()
                .delete("/carts/?productId=" + productId)
                .then()
                .extract();

        assertThat(cartDao.findByMemberIdAndProductId(memberId, productId)).isEmpty();
        assertThat(deleteResult.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Authorization 헤더에 잘못된 email이 들어가 있으면 401 Unauthorized를 반환한다.")
    void unauthorizedTestWithInvalidEmail() {
        final String email = "invalid@wooteco.com";
        final String password = "mangopassword";
        final var result = given()
                .auth().preemptive().basic(email, password)
                .when()
                .get("/carts")
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Authorization 헤더에 잘못된 password가 들어가 있으면 401 Unauthorized를 반환한다.")
    void unauthorizedTestWithInvalidPassword() {
        final String email = "mango@wooteco.com";
        final String password = "invalid";
        final var result = given()
                .auth().preemptive().basic(email, password)
                .when()
                .get("/carts")
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Authorization 헤더에 값이 없으면 401 Unauthorized를 반환한다.")
    void unauthorizedTestWithEmptyHeader() {
        final var result = given()
                .when()
                .get("/carts")
                .then()
                .extract();
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
