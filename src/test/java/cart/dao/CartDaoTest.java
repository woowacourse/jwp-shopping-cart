package cart.dao;

import cart.domain.Cart;
import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    CartDao cartDao;
    MemberDao memberDao;
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @Test
    @Sql("/truncate.sql")
    void save_and_findById_success() {
        Cart cart = new Cart(1L, List.of());

        //when
        cartDao.save(cart.getMemberId(), Collections.emptyList());

        //then
        assertThat(cartDao.findByMemberId(cart.getMemberId())).isEqualTo(cart);
    }

    @Test
    @Sql("/truncate.sql")
    void insertProduct_success() {
        //given
        Cart cart = new Cart(1L, List.of());
        cartDao.save(cart.getMemberId(), Collections.emptyList());
        long savedId1 = productDao.save(new Product("product2", "image222", 200L));
        long savedId2 = productDao.save(new Product("product3", "image333", 200L));

        //when
        cartDao.insertProduct(1L, savedId1);
        cartDao.insertProduct(1L, savedId2);

        //then
        assertThat(cartDao.findByMemberId(1L).getProducts()).containsExactly(new Product(savedId1, "product2", "image222", 200L), new Product(savedId2, "product3", "image333", 200L));
    }

    @Test
    @Sql("/truncate.sql")
    void delete_success() {
        //given
        long savedId = productDao.save(new Product(3L, "name1", "image2", 1000L));
        cartDao.save(1L, List.of(savedId));

        //when
        cartDao.deleteProduct(1L, savedId);

        //then
        assertThat(cartDao.findByMemberId(1L).getProducts()).isEmpty();
    }
}
