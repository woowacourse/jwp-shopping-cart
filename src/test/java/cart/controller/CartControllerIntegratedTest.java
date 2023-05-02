package cart.controller;

import cart.cart.dao.CartDao;
import cart.cart.dto.CartProductResponse;
import cart.cart.dto.CartResponse;
import cart.config.DBTransactionExecutor;
import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerIntegratedTest {
    private static final String DEFAULT_PATH = "/carts/";
    
    @LocalServerPort
    private int port;
    
    private final ProductDao productDao;
    private final CartDao cartDao;
    private final MemberDao memberDao;
    
    @RegisterExtension
    private DBTransactionExecutor dbTransactionExecutor;
    
    @Autowired
    public CartControllerIntegratedTest(final ProductDao productDao, final CartDao cartDao, final MemberDao memberDao, final JdbcTemplate jdbcTemplate) {
        this.dbTransactionExecutor = new DBTransactionExecutor(jdbcTemplate);
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }
    
    @BeforeEach
    void setUp() {
        productDao.save(new Product("product1", "a.com", 1000));
        productDao.save(new Product("product2", "b.com", 2000));
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
    void 이미_장바구니에_존재하는_물품을_저장할_시_예외처리() {
        // given
        cartDao.save(1L, 2L);
        
        // expect
        RestAssured.given().log().all()
                .auth().preemptive().basic("b@b.com", "password2")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("[ERROR] 해당 계정엔 해당 물품이 이미 장바구니에 존재합니다."));
    }
    
    @Test
    void MemberRequest를_전달하면_장바구니_상품들을_가져온다() {
        // given
        productDao.save(new Product("product3", "c.com", 3000));
        cartDao.save(2L, 1L);
        cartDao.save(2L, 2L);
        cartDao.save(3L, 1L);
        cartDao.save(3L, 2L);
        
        // when
        final List<CartProductResponse> products = RestAssured.given().log().all()
                .auth().preemptive().basic("b@b.com", "password2")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList("", CartProductResponse.class);
        
        // then
        final ProductResponse firstProduct = new ProductResponse(2L, "product2", "b.com", 2000);
        final ProductResponse secondProduct = new ProductResponse(3L, "product3", "c.com", 3000);
        final CartProductResponse expectFirstCartProduct = CartProductResponse.from(2L, firstProduct);
        final CartProductResponse expectSecondCartProduct = CartProductResponse.from(4L, secondProduct);
        assertThat(products).containsExactly(expectFirstCartProduct, expectSecondCartProduct);
    }
    
    @Test
    void carId와_memberId를_전달하면_해당_카트_품목을_삭제한다() {
        cartDao.save(2L, 2L);
        cartDao.save(1L, 1L);
        
        RestAssured.given().log().all()
                .auth().preemptive().basic("b@b.com", "password2")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
