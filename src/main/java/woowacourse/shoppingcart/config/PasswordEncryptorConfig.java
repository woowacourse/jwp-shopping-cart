package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.PasswordSecureHashEncryptor;

@Configuration
public class PasswordEncryptorConfig {

    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new PasswordSecureHashEncryptor();
    }
}
