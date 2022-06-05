package woowacourse.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "security.jwt.token")
@ConstructorBinding
public class TokenProperty {

    private final String secretKey;
    private final long expireLength;

    public TokenProperty(String secretKey, long expireLength) {
        this.secretKey = secretKey;
        this.expireLength = expireLength;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpireLength() {
        return expireLength;
    }
}
