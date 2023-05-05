package cart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class CartDaoTest {

    @Autowired
    private DataSource dataSource;
    private CartDao cartDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setting() {
        cartDao = new CartDao(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @DisplayName("존재하지 않는 productId 를 장바구니에 넣으려 한담녀 예외가 발생한다. data.sql 에 의해서 생성 되어있다.")
    @Test
    void addProduct_invalid_nonexistentProductId() {
        //given
        int nonexistentProductId = 1000;
        int exitingMemberId = 1;

        //when
        final int findUser = jdbcTemplate.query("select * from member where id = ?", nullMapper(), exitingMemberId).size();
        final int findProduct = jdbcTemplate.query("select * from product where id = ?", nullMapper(), nonexistentProductId).size();

        //then
        assertAll(
                () -> assertThat(findUser).isEqualTo(1),
                () -> assertThat(findProduct).isEqualTo(0),
                () -> assertThatThrownBy(() -> cartDao.addProduct(exitingMemberId, nonexistentProductId))
                        .isInstanceOf(DataIntegrityViolationException.class)
        );
    }

    @DisplayName("존재하지 않는 memberId 를 장바구니에 넣으려 한담녀 예외가 발생한다. data.sql 에 의해서 생성 되어있다.")
    @Test
    void addProduct_invalid_nonexistentMemberId() {
        //given
        int exitingProductId = 1;
        int nonexistentMemberId = 10000;

        //when
        final int findUser = jdbcTemplate.query("select * from member where id = ?", nullMapper(), exitingProductId).size();
        final int findProduct = jdbcTemplate.query("select * from product where id = ?", nullMapper(), nonexistentMemberId).size();

        //then
        assertAll(
                () -> assertThat(findUser).isEqualTo(1),
                () -> assertThat(findProduct).isEqualTo(0),
                () -> assertThatThrownBy(() -> cartDao.addProduct(exitingProductId, nonexistentMemberId))
                        .isInstanceOf(DataIntegrityViolationException.class)
        );
    }

    @DisplayName("존재하는 유저와 상품이라면 장바구니에 등록한다")
    @Test
    void addProduct() {
        //given
        int exitingProductId = 1;
        int exitingMemberId = 1;

        //when
        final int originSize = jdbcTemplate.query("select * from cart", nullMapper()).size();
        cartDao.addProduct(exitingMemberId, exitingProductId);
        final int afterSize = jdbcTemplate.query("select * from cart", nullMapper()).size();

        //then
        assertThat(afterSize).isEqualTo(originSize + 1);
    }

    @DisplayName("cart 데이터를 삭제한다")
    @Test
    void deleteProduct() {
        //given
        int exitingProductId = 1;
        int exitingMemberId = 1;
        cartDao.addProduct(exitingMemberId, exitingProductId);

        //when
        final int originSize = jdbcTemplate.query("select * from cart", nullMapper()).size();
        cartDao.deleteProduct(exitingMemberId, exitingProductId);
        final int afterSize = jdbcTemplate.query("select * from cart", nullMapper()).size();

        //then
        assertThat(afterSize).isEqualTo(originSize - 1);
    }

    private RowMapper<Object> nullMapper() {
        return (ig, ig2) -> null;
    }
}
