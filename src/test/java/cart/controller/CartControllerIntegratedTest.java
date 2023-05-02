package cart.controller;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartResponse;
import cart.member.dao.MemberDao;
import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static cart.constant.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerIntegratedTest {
    private static final String DEFAULT_PATH = "/carts/";
    
    @LocalServerPort
    private int port;
    
    private final ProductDao productDao;
    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CartControllerIntegratedTest(final ProductDao productDao, final CartDao cartDao, final MemberDao memberDao, final JdbcTemplate jdbcTemplate) {
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @BeforeEach
    void setUp() {
        productDao.save(new Product("product1", "product1@product.com", 1000));
        productDao.save(new Product("product2", "product2@product.com", 2000));
        RestAssured.port = port;
    }
    
    @Test
    void 멤버_정보의_인증을_진행한_뒤_장바구니를_저장한다() {
        // when
        CartResponse cart = RestAssured.given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CartResponse.class);
        
        // then
        assertThat(cart).isEqualTo(new CartResponse(1L, 1L, 1L));
    }
    
    @AfterEach
    void tearDown() {
        cartDao.deleteAll();
        productDao.deleteAll();
        memberDao.deleteAll();
        jdbcTemplate.execute(CART_ID_INIT_SQL);
        jdbcTemplate.execute(PRODUCT_ID_INIT_SQL);
        jdbcTemplate.execute(MEMBER_ID_INIT_SQL);
    }
}
