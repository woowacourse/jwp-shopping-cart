package cart.dao.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import(CartUserDao.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartUserDaoTest {

    @Autowired
    private CartUserDao cartUserDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("사용자 이메일로 사용자 조회 테스트")
    @Test
    void findByEmail() {
        String findTargetEmail = "a@a.com";
        jdbcTemplate.update("INSERT INTO cart_user (email, cart_password)\n"
                + "values ('a@a.com', 'password1'),\n"
                + "       ('b@b.com', 'password2');");

        CartUserEntity user = cartUserDao.findByEmail(findTargetEmail);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(findTargetEmail);
    }
}
