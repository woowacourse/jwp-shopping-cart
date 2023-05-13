package cart.dao.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.dao.product.JdbcProductDao;
import cart.dao.product.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@JdbcTest
@Import(JdbcCartDao.class)
class JdbcCartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        cartDao = new JdbcCartDao(jdbcTemplate);
        productDao = new JdbcProductDao(jdbcTemplate);
    }

    private Long insertProduct() {
        final Product product = new Product(new Name("테스트"), "https://test", new Price(1000));
        return productDao.insert(product);
    }

    private Long insertMember() {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", "test@wooteco.com");
        parameters.put("password", "test");
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void insert() {
        // given
        final Long productId = insertProduct();
        final Long memberId = insertMember();
        final Cart cart = new Cart(productId, memberId);
        // when
        cartDao.insert(cart);
        final Cart insertedCart = cartDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(NoSuchElementException::new);
        // then
        assertEquals(cart.getProductId(), insertedCart.getProductId());
        assertEquals(cart.getMemberId(), insertedCart.getMemberId());
        assertEquals(1, insertedCart.getQuantity().getValue());
    }

    @Test
    @DisplayName("장바구니 목록을 모두 조회할 수 있다.")
    void findAll() {
        // given
        final Long memberId = insertMember();
        final Long productId = insertProduct();
        final Cart cart = new Cart(productId, memberId);
        cartDao.insert(cart);
        // when & then
        assertEquals(1, cartDao.findAll().size());
    }

    @Test
    @DisplayName("memberId와 productId로 장바구니를 조회할 수 있다.")
    void findByMemberIdAndProductId() {
        // given
        final Long memberId = insertMember();
        final Long productId = insertProduct();
        final Cart cart = new Cart(productId, memberId);
        cartDao.insert(cart);
        // when
        final Cart result = cartDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(NoSuchElementException::new);
        // then
        assertEquals(cart.getProductId(), result.getProductId());
        assertEquals(cart.getMemberId(), result.getMemberId());
        assertEquals(1, result.getQuantity().getValue());
    }

    @Test
    @DisplayName("동일한 memberId와 productId를 count 해서 수량을 집계할 수 있다.")
    void countQuantity() {
        // given
        final Long memberId = insertMember();
        final Long productId = insertProduct();
        final Cart cart = new Cart(productId, memberId);
        cartDao.insert(cart);
        cartDao.insert(cart);
        cartDao.insert(cart);
        // when
        final Cart result = cartDao.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(NoSuchElementException::new);
        // then
        assertEquals(cart.getProductId(), result.getProductId());
        assertEquals(cart.getMemberId(), result.getMemberId());
        assertEquals(3, result.getQuantity().getValue());
    }

    @Test
    @DisplayName("memberId와 productId로 장바구니를 삭제할 수 있다.")
    void deleteByMemberIdAndProductId() {
        // given
        final Long memberId = insertMember();
        final Long productId = insertProduct();
        final Cart cart = new Cart(productId, memberId);
        cartDao.insert(cart);
        // when
        cartDao.deleteByMemberIdAndProductId(memberId, productId);
        // then
        assertEquals(0, cartDao.findAll().size());
    }
}
