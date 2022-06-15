package woowacourse.auth.domain;

@FunctionalInterface
public interface EncryptionStrategy {

	String encode(Password password);
}
