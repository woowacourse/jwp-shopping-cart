package woowacourse.auth.support;

import static java.nio.charset.StandardCharsets.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import woowacourse.auth.domain.EncryptionStrategy;
import woowacourse.auth.domain.Password;

@Component
public class MessageDigestEncryption implements EncryptionStrategy {

	private static final String ALGORITHM = "SHA";
	private static final String FORMAT = "%02X";

	@Override
	public String encode(Password password) {
		String input = password.getValue();
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			digest.update(input.getBytes(UTF_8));

			StringBuilder encrypted = new StringBuilder();
			for (byte each : digest.digest()) {
				encrypted.append(String.format(FORMAT, each));
			}
			return encrypted.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("암호하할 수 없습니다.");
		}
	}
}
