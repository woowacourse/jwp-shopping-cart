package cart.dao;

import cart.dao.cart.DbCartDao;
import cart.domain.Cart;
import cart.dto.CartDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@JdbcTest
class DbCartDaoTest {
    public static final CartDto CART_DTO_A = new CartDto(1L, 1L);
    public static final CartDto CART_DTO_B = new CartDto(2L, 1L);

    DbCartDao dbCartDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        dbCartDao = new DbCartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 저장한다.")
    void save() {
        dbCartDao.save(CART_DTO_A);

        String sql = "select count(*) from cart";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 상품을 제거한다.")
    void delete() {
        dbCartDao.save(CART_DTO_A);
        dbCartDao.save(CART_DTO_B);

        dbCartDao.delete(CART_DTO_A);

        String sql = "select count(*) from cart";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니의 모든 상품을 memberId를 통해 불러온다.")
    void findAll() {
        dbCartDao.save(CART_DTO_A);
        dbCartDao.save(CART_DTO_B);

        List<Cart> carts = dbCartDao.findAll(CART_DTO_A.getMemeberId());

        Assertions.assertThat(carts.get(0).getMemberId()).isEqualTo(CART_DTO_A.getMemeberId());
        Assertions.assertThat(carts.get(0).getProductId()).isEqualTo(CART_DTO_A.getProductId());
    }

}