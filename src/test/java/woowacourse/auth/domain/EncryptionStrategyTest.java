package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.support.MessageDigestEncryption;

class EncryptionStrategyTest {

	private final EncryptionStrategy encryptionStrategy = new MessageDigestEncryption();

	@DisplayName("문자열을 암호화하면 다른 문자열이 나온다.")
	@Test
	void encrypt() {
		// given
		String input = "asdwe12@#";

		// when
		String encode = encryptionStrategy.encode(new Password(input));

		// then
		assertThat(input).isNotEqualTo(encode);
		System.out.println(encode);
	}

	@DisplayName("같은 문자열을 암호화하면 같은 값이 나온다.")
	@Test
	void encryptWithSameInput() {
		// given
		 String input1 = "asdwe12@#";
		 String input2 = "asdwe12@#";

		// when // then
		assertThat(encryptionStrategy.encode(new Password(input1)))
			.isEqualTo(encryptionStrategy.encode(new Password(input2)));
	}
}
