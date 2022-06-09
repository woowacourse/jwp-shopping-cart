package woowacourse;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import woowacourse.auth.support.JwtTokenProvider;

@TestConfiguration
public class ControllerTestConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(
                "abfrbGciOiJIUzI1TeIsInR5cCI6IkpXVCJ9."
                        + "eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLOPpYXQiOjE1MTYyMzkwMjJ1."
                        + "ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt29Lno",
                60000);
    }
}
