package cart.settings.service;

import cart.settings.dao.UserDAO;
import cart.settings.domain.Email;
import cart.settings.domain.Password;
import cart.settings.domain.User;
import cart.settings.dto.UserRequestDTO;
import cart.settings.dto.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SettingsServiceTest {

    public static final List<User> TEST_USERS = List.of(
            new User(1L, new Email("test1@test.com"), new Password("test1333#")),
            new User(2L, new Email("test2@test.com"), new Password("test2333#")),
            new User(3L, new Email("test3@test.com"), new Password("test3333#"))
    );

    @Test
    @DisplayName("서비스 findAllUsers 테스트")
    void findAllUsers() {
        //when
        final SettingsService settingsService = new SettingsService(new fakeUserDAO());
        final List<UserResponseDTO> users = settingsService.findAllUsers();

        //then
        final int expectedSize = 3;
        assertThat(users.size()).isEqualTo(expectedSize);

        final UserResponseDTO firstUser = users.get(0);
        final String email = TEST_USERS.get(0).getEmail().getValue();
        assertThat(firstUser.getEmail()).isEqualTo(email);
    }

    static class fakeUserDAO implements UserDAO {

        @Override
        public boolean isExist(final UserRequestDTO userRequestDTO) {
            return false;
        }

        @Override
        public User create(final UserRequestDTO userRequestDTO) {
            return null;
        }

        @Override
        public User find(final UserRequestDTO userRequestDTO) {
            return null;
        }

        @Override
        public List<User> findAll() {
            return TEST_USERS;
        }

        @Override
        public void delete(final User user) {

        }
    }

}