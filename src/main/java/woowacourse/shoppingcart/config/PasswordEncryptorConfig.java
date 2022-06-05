package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import woowacourse.shoppingcart.domain.customer.PasswordBcryptEncryptor;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;

@Configuration
public class PasswordEncryptorConfig {

    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new PasswordBcryptEncryptor(new BCryptPasswordEncoder());
    }
}
