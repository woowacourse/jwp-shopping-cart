package cart.settings.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.auth.dao.UserDAO;
import cart.auth.domain.Email;
import cart.auth.domain.Password;
import cart.auth.domain.User;
import cart.auth.dto.UserInfo;
import cart.auth.dto.UserResponseDTO;
import cart.auth.service.AuthService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthServiceTest {
    
    static class fakeUserDAO implements UserDAO {
        
        @Override
        public boolean isExist(final UserInfo userInfo) {
            return false;
        }
        
        @Override
        public User create(final UserInfo userInfo) {
            return null;
        }
        
        @Override
        public User find(final UserInfo userInfo) {
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
    
    public static final List<User> TEST_USERS = List.of(
            new User(1L, new Email("test1@test.com"), new Password("test1333#")),
            new User(2L, new Email("test2@test.com"), new Password("test2333#")),
            new User(3L, new Email("test3@test.com"), new Password("test3333#"))
    );
    
    @Test
    @DisplayName("서비스 findAllUsers 테스트")
    void findAllUsers() {
        //when
        final AuthService settingsService = new AuthService(new fakeUserDAO());
        final List<UserResponseDTO> users = settingsService.findAllUsers();
        
        //then
        final int expectedSize = 3;
        assertThat(users.size()).isEqualTo(expectedSize);
        
        final UserResponseDTO firstUser = users.get(0);
        final String email = TEST_USERS.get(0).getEmail().getValue();
        assertThat(firstUser.getEmail()).isEqualTo(email);
    }
    
}