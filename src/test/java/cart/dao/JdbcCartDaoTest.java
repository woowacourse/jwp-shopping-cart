package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@JdbcTest
class JdbcCartDaoTest {

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNum) ->
            new Cart(
                    resultSet.getLong("id"),
                    resultSet.getLong("member_id"),
                    resultSet.getLong("product_id")
            );

    private JdbcMemberDao jdbcMemberDao;
    private JdbcProductDao jdbcProductDao;
    private JdbcCartDao jdbcCartDao;

    public JdbcCartDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcMemberDao = new JdbcMemberDao(jdbcTemplate);
        this.jdbcProductDao = new JdbcProductDao(jdbcTemplate);
        this.jdbcCartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Cart 삽입")
    void insert() {
        // given
        Long memberId = jdbcMemberDao.insert(new Member("test@test.com", "password1", "애쉬"));
        Long productId = jdbcProductDao.insert(new Product("피자", 1000, null));

        // when
        Long cartId = jdbcCartDao.insert(new Cart(memberId, productId));

        // then
        assertThat(cartId).isPositive();
    }

    @Test
    @DisplayName("멤버별 Cart 조회")
    void findAllByMemberId() {
        // given
        Long memberId = jdbcMemberDao.insert(new Member("test@test.com", "password1", "조개소년"));
        Long memberId2 = jdbcMemberDao.insert(new Member("test2@test.com", "password1", "비버"));
        Long productId = jdbcProductDao.insert(new Product("피자", 1000, null));
        Long productId2 = jdbcProductDao.insert(new Product("햄버거", 2000, null));
        jdbcCartDao.insert(new Cart(memberId, productId));
        jdbcCartDao.insert(new Cart(memberId, productId2));
        jdbcCartDao.insert(new Cart(memberId2, productId));

        // when
        List<Cart> carts = jdbcCartDao.findAllByMemberId(memberId);

        // then
        assertAll(
                () -> assertThat(carts.size()).isEqualTo(2),
                () -> assertThat(carts).extracting("memberId").containsExactly(memberId, memberId),
                () -> assertThat(carts).extracting("productId").containsExactlyInAnyOrder(productId, productId2)
        );
    }

    @Test
    @DisplayName("멤버와 상품 id로 Cart 조회")
    void findByMemberIdAndProductId() {
        // given
        Long memberId = jdbcMemberDao.insert(new Member("test@test.com", "password1", "조개소년"));
        Long productId = jdbcProductDao.insert(new Product("피자", 1000, null));
        jdbcCartDao.insert(new Cart(memberId, productId));

        // when
        Optional<Cart> cart = jdbcCartDao.findByMemberIdAndProductId(memberId, productId);

        // then
        assertAll(
                () -> assertThat(cart).isPresent(),
                () -> assertThat(cart.get())
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(new Cart(memberId, productId))
        );
    }

    @Test
    @DisplayName("Cart 조회시 멤버와 상품 id가 존재하지 않을 때 empty 객체 반환")
    void findByMemberIdAndProductId_empty() {
        // given
        Long memberId = jdbcMemberDao.insert(new Member("test@test.com", "password1", "조개소년"));
        Long productId = jdbcProductDao.insert(new Product("피자", 1000, null));

        // when
        Optional<Cart> cart = jdbcCartDao.findByMemberIdAndProductId(memberId, productId);

        // then
        assertThat(cart).isEmpty();
    }

    @Test
    @DisplayName("Cart 삭제")
    void delete() {
        // given
        Long memberId = jdbcMemberDao.insert(new Member("test@test.com", "password1", "조개소년"));
        Long productId = jdbcProductDao.insert(new Product("피자", 1000, null));
        Long cartId = jdbcCartDao.insert(new Cart(memberId, productId));

        // when
        jdbcCartDao.delete(memberId, productId);

        // then
        Optional<Cart> cart = jdbcCartDao.findByMemberIdAndProductId(memberId, productId);
        assertThat(cart).isEmpty();
    }
}
