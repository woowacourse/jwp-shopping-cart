package cart.service;

import cart.exception.AuthPrincipalInValidException;
import cart.repository.JdbcMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({BasicAuthService.class, JdbcMemberRepository.class})
class BasicAuthServiceTest {

    private static final String VALID_CREDENTIAL = "aG9uZ1NpbGVAd29vdGVjby5jb206aG9uZ1NpbGU=";

    @Autowired
    private BasicAuthService basicAuthService;

    @Nested
    class authorizeTest {

        @ParameterizedTest
        @DisplayName("principal이 Basic이 아니면 Exception 발생")
        @ValueSource(strings = {"Barer", "JSESSION_ID"})
        void principalInValid(final String principal) {
            final Runnable authorizeInValidPrincipal = () -> basicAuthService.authorize(principal, VALID_CREDENTIAL);

            Assertions.assertThatThrownBy(authorizeInValidPrincipal::run)
                    .isInstanceOf(AuthPrincipalInValidException.class);
        }
    }
}
