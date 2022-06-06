package woowacourse.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import woowacourse.auth.domain.BcryptPasswordMatcher;
import woowacourse.auth.domain.PasswordMatcher;
import woowacourse.shoppingcart.domain.customer.BcryptPasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;

@Configuration
public class PasswordSecurityConfig {

    @Bean
    public PasswordEncryptor getPasswordEncryptor() {
        return new BcryptPasswordEncryptor();
    }

    @Bean
    public PasswordMatcher getPasswordMatcher() {
        return new BcryptPasswordMatcher();
    }
}
