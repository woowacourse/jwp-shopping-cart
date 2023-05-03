package cart.settings.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.settings.dao.UserDAO;
import cart.settings.domain.Email;
import cart.settings.domain.Password;
import cart.settings.domain.User;
import cart.settings.dto.ResponseUserDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SettingsServiceTest {
    
    static class fakeUserDAO implements UserDAO {
        
        @Override
        public void insert(final User user) {
        
        }
        
        @Override
        public boolean isExist(final String email) {
            return false;
        }
        
        @Override
        public boolean isCorrectPassword(final User user) {
            return false;
        }
        
        @Override
        public User findByEmail(final String email) {
            return null;
        }
        
        @Override
        public List<User> findAll() {
            return TEST_USERS;
        }
    }
    
    public static final List<User> TEST_USERS = List.of(
            new User(new Email("test1@test.com"), new Password("test1333#")),
            new User(new Email("test2@test.com"), new Password("test2333#")),
            new User(new Email("test3@test.com"), new Password("test3333#"))
    );
    
    @Test
    @DisplayName("서비스 findAllUsers 테스트")
    void findAllUsers() {
        //when
        final SettingsService settingsService = new SettingsService(new fakeUserDAO());
        final List<ResponseUserDTO> users = settingsService.findAllUsers();
        
        //then
        final int expectedSize = 3;
        assertThat(users.size()).isEqualTo(expectedSize);
        
        final ResponseUserDTO firstUser = users.get(0);
        final String email = TEST_USERS.get(0).getEmail().getValue();
        assertThat(firstUser.getEmail()).isEqualTo(email);
    }
    
}