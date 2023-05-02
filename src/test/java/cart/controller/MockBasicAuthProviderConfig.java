package cart.controller;

import cart.infrastructure.BasicAuthProvider;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockBasicAuthProviderConfig {

    @Bean
    public BasicAuthProvider basicAuthProvider() {
        return Mockito.mock(BasicAuthProvider.class);
    }
}