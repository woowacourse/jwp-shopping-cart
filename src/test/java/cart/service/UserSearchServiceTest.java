package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import cart.domain.user.User;
import cart.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSearchServiceTest {

    private UserSearchService userSearchService;

    @BeforeEach
    void setUp() {
        final StubUserRepository stubUserRepository = new StubUserRepository();
        userSearchService = new UserSearchService(stubUserRepository);
    }

    @Test
    void 전체_조회_테스트() {
        final List<User> users = userSearchService.findAll();
        assertThat(users).isNull();
    }
}
