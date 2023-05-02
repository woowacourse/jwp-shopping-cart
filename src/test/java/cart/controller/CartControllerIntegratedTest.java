package cart.controller;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartResponse;
import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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
        productDao.save(new Product("product1", "a.com", 1000));
        productDao.save(new Product("product2", "b.com", 2000));
        
        memberDao.deleteAll();
        jdbcTemplate.execute(MEMBER_ID_INIT_SQL);
        memberDao.save(new Member("a@a.com", "password1"));
        memberDao.save(new Member("b@b.com", "password2"));
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
    
    @Test
    void MemberRequest를_전달하면_장바구니_상품들을_가져온다() {
        productDao.save(new Product("product3", "c.com", 3000));
        cartDao.save(2L, 2L);
        cartDao.save(3L, 2L);
        
        // when
        final List<ProductResponse> products = RestAssured.given().log().all()
                .auth().preemptive().basic("b@b.com", "password2")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList("", ProductResponse.class);
        
        // then
        final ProductResponse expectFirstProduct = new ProductResponse(2L, "product2", "b.com", 2000);
        final ProductResponse expectSecondProduct = new ProductResponse(3L, "product3", "c.com", 3000);
        assertThat(products).containsExactly(expectFirstProduct, expectSecondProduct);
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
