package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCustomerException;

class EmailTest {

	@DisplayName("이메일 형식이 틀리면 예외가 발생한다.")
	@ParameterizedTest
	@ValueSource(strings = {"asdf", "asdfgmail", "asdgmailcom", "asd @ gmail. com", "asdgmail.com"})
	void exception(String email) {
		try {
			new Email(email);
			throw new IllegalStateException();
		} catch (InvalidCustomerException exception) {
			assertThat(exception.getCode()).isEqualTo(ErrorCode.EMAIL_FORMAT);
		}
	}
}
