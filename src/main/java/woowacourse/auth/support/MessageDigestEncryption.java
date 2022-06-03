package woowacourse.auth.support;

import static java.nio.charset.StandardCharsets.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import woowacourse.auth.domain.Password;

@Component
public class MessageDigestEncryption implements EncryptionStrategy {

	@Override
	public String encode(Password password) {
		String input = password.getValue();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(input.getBytes(UTF_8));

			StringBuilder encrypted = new StringBuilder();
			for (byte each : digest.digest()) {
				encrypted.append(String.format("%02X", each));
			}
			return encrypted.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("암호하할 수 없습니다.");
		}
	}
}
