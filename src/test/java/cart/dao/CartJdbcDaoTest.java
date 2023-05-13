package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:testData.sql")
class CartJdbcDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void init() {
        cartDao = new CartJdbcDao(jdbcTemplate);
    }

    @DisplayName("장바구니 추가 테스트")
    @Test
    void addCart() {
        // given
        Integer memberId = jdbcTemplate.queryForObject("select id from Member limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        Integer productId = jdbcTemplate.queryForObject("select id from Product limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        CartEntity cartEntityForAdd = new CartEntity(productId, memberId);

        // when
        cartDao.addCart(cartEntityForAdd);
        List<CartEntity> findCartsByMemberId = cartDao.findCartByMemberId(memberId);

        // then
        assertThat(findCartsByMemberId).hasSize(1);
    }

    @DisplayName("장바구니 멤버로 조회 테스트")
    @Test
    void findCartByMemberId() {
        // given
        Integer memberId = jdbcTemplate.queryForObject("select id from Member limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        Integer productId = jdbcTemplate.queryForObject("select id from Product limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        CartEntity cartEntityForAdd = new CartEntity(productId, memberId);
        cartDao.addCart(cartEntityForAdd);

        // when
        List<CartEntity> findCartsByMemberId = cartDao.findCartByMemberId(memberId);

        // then
        assertThat(findCartsByMemberId.get(0).getMemberId()).isEqualTo(memberId);
    }

    @DisplayName("장바구니 삭제 테스트")
    @Test
    void deleteById() {
        // given
        Integer memberId = jdbcTemplate.queryForObject("select id from Member limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        Integer productId = jdbcTemplate.queryForObject("select id from Product limit 1",
                (rs, rowNum) -> rs.getInt("id"));
        CartEntity cartEntityForAdd = new CartEntity(productId, memberId);
        cartDao.addCart(cartEntityForAdd);

        CartEntity cartEntityForAdd2 = new CartEntity(productId, memberId);
        cartDao.addCart(cartEntityForAdd2);

        List<CartEntity> cartsByMemberId = cartDao.findCartByMemberId(memberId);
        Integer cartId = cartsByMemberId.get(0).getId();

        // when
        cartDao.deleteById(cartId);
        List<CartEntity> cartsByMemberId2 = cartDao.findCartByMemberId(memberId);

        // then
        assertThat(cartsByMemberId2).hasSize(1);
    }

}
