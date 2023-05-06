package cart.dao;

import cart.domain.product.Product;
import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:test.sql"})
public class JdbcCartDaoTest {

    private final JdbcCartDao jdbcCartDao;
    private final JdbcProductDao jdbcProductDao;
    private final JdbcUserDao jdbcUserDao;

    private JdbcCartDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcCartDao = new JdbcCartDao(jdbcTemplate);
        this.jdbcProductDao = new JdbcProductDao(jdbcTemplate);
        this.jdbcUserDao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Cart 삽입 테스트")
    void insert() {
        final long productId = jdbcProductDao.insert(new Product("name", 1, null));
        final long userId = jdbcUserDao.insert(new User("io@mail.com", "password"));

        final long id = jdbcCartDao.insert(userId, productId);

        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("Cart UserId로 조회 테스트")
    void findById() {
        final long productId1 = jdbcProductDao.insert(new Product("name", 1000, null));
        final long productId2 = jdbcProductDao.insert(new Product("name", 2000, null));
        final long userId = jdbcUserDao.insert(new User("io@mail.com", "password"));

        jdbcCartDao.insert(userId, productId1);
        jdbcCartDao.insert(userId, productId2);

        assertThat(jdbcCartDao.findByUserId(userId)).extracting("productId").contains(productId1, productId2);
    }

    @Test
    @DisplayName("cart 삭제 테스트")
    void delete() {
        final long productId = jdbcProductDao.insert(new Product("name", 1000, null));
        final long userId = jdbcUserDao.insert(new User("io@mail.com", "password"));
        final long id = jdbcCartDao.insert(userId, productId);

        jdbcCartDao.deleteById(id);

        assertThat(jdbcCartDao.findByUserId(userId).size()).isEqualTo(0);
    }
}
