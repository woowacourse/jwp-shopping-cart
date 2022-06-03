package woowacourse.auth.support;

import woowacourse.auth.domain.Password;

@FunctionalInterface
public interface EncryptionStrategy {

	String encode(Password password);
}
