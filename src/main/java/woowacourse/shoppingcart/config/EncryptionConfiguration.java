package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import woowacourse.shoppingcart.domain.Encryption.EncryptionSHA256Strategy;
import woowacourse.shoppingcart.domain.Encryption.EncryptionStrategy;

@Configuration
public class EncryptionConfiguration {

    @Bean
    public EncryptionStrategy registerBeanEncryptionStrategy() {
        return new EncryptionSHA256Strategy();
    }
}
