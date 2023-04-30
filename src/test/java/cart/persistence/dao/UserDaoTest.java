package cart.persistence.dao;

import cart.persistence.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(UserDao.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DisplayName("사용자 정보를 저장한다.")
    void insert() {
        // given
        final UserEntity userEntity = new UserEntity("journey@gmail.com", "password", "져니", "010-1234-5678");

        // when
        final Long savedUserId = userDao.insert(userEntity);

        // then
        final Optional<UserEntity> user = userDao.findById(savedUserId);
        final UserEntity findUser = user.get();
        assertThat(findUser)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효한 사용자 아이디가 주어지면, 사용자 정보를 조회한다.")
    void findById_success() {
        // given
        final UserEntity userEntity = new UserEntity("journey@gmail.com", "password", "져니", "010-1234-5678");
        final Long savedUserId = userDao.insert(userEntity);

        // when
        final Optional<UserEntity> user = userDao.findById(savedUserId);

        // then
        final UserEntity findUser = user.get();
        assertThat(findUser)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 아이디가 주어지면, 빈 값을 반환한다.")
    void findById_empty() {
        // when
        final Optional<UserEntity> user = userDao.findById(1L);

        // then
        assertThat(user).isEmpty();
    }
}
