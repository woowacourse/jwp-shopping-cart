package cart.dao.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.user.CartUser;
import cart.domain.user.UserEmail;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import({CartCartUserRepositoryImpl.class, CartUserDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartCartUserRepositoryImplTest {

    @Autowired
    private CartCartUserRepositoryImpl cartUserRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("사용자 이메일로 사용자 조회시 존재하지 않으면 예외 발생")
    @Test
    void findByEmailFailureNotExist() {
        String findTargetEmail = "a@a.com";

        assertThatThrownBy(() -> cartUserRepository.findByEmail(findTargetEmail))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("사용자 이메일로 사용자 조회시 존재하지 않으면 예외 발생")
    @Test
    void findByEmail() {
        String findTargetEmail = "a@a.com";
        jdbcTemplate.update("INSERT INTO cart_user (email, cart_password)\n"
                + "values ('a@a.com', 'password1'),\n"
                + "       ('b@b.com', 'password2');");

        CartUser user = cartUserRepository.findByEmail(findTargetEmail);

        assertThat(user).isNotNull();
        assertThat(user.getUserEmail()).isEqualTo(findTargetEmail);
    }

    @DisplayName("사용자 저장 테스트")
    @Test
    void saveUser() {
        String email = "a@a.com";
        CartUser cartUser = new CartUser(
                UserEmail.from(email),
                "password"
        );

        cartUserRepository.save(cartUser);

        CartUser user = cartUserRepository.findByEmail(email);

        assertThat(user).isNotNull();
        assertThat(user.getUserEmail()).isEqualTo(email);
    }
}
