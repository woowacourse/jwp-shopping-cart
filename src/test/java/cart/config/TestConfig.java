package cart.config;

import cart.auth.AuthSubjectArgumentResolver;
import org.slf4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {
    @MockBean
    private Logger logger;
    
    @MockBean
    private AuthSubjectArgumentResolver resolver;
}
