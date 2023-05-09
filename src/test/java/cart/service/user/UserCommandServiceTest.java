package cart.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.repository.user.StubUserRepository;
import cart.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    private UserCommandService userCommandService;
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        userRepository = new StubUserRepository();
        userCommandService = new UserCommandService(userRepository, applicationEventPublisher);
    }

    @Test
    void 저장_테스트() {
        // given
        final String email = "asdf";
        final String password = "1234";

        // when
        userCommandService.save(email, password);

        // then
        assertAll(
                () -> assertThat(userRepository.existsByEmailAndPassword(email, password)).isTrue(),
                () -> assertThat(userRepository.findByEmail(email)).isPresent()
        );
    }
}
