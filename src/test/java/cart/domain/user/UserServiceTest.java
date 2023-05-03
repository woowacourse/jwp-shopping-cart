package cart.domain.user;

import cart.domain.TestFixture;
import cart.domain.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("모든 유저 조회 테스트")
    @Test
    void findAllUsers() {
        //given
        User userA = TestFixture.ZUNY;
        User userB = TestFixture.ADMIN;

        userRepository.save(userA);
        userRepository.save(userB);

        //when
        List<UserDto> allUsers = userService.getAllUsers();

        //then
        assertThat(allUsers).hasSize(2);
        assertThat(allUsers).extractingResultOf("getEmail")
                .containsExactlyInAnyOrder(userA.getEmail(), userB.getEmail());
    }
}
