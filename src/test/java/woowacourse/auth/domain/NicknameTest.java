package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCustomerException;

class NicknameTest {

	@DisplayName("닉네임 길이가 2~10자가 아니면 예외가 발생한다.")
	@ParameterizedTest
	@ValueSource(strings = {"더", "123456789011", ""})
	void exception(String name) {
		try {
			new Nickname(name);
			throw new IllegalStateException();
		} catch (InvalidCustomerException exception) {
			assertThat(exception.getCode()).isEqualTo(ErrorCode.NICKNAME_FORMAT);
		}
	}
}
