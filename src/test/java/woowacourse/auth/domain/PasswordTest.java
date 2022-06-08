package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCustomerException;

class PasswordTest {

	@DisplayName("비밀번호 형식이 안맞으면 예외가 발생한다.")
	@ParameterizedTest
	@ValueSource(strings = {"dk", "1802", "dk1802", "dk*", "1802&", "", "df s2!", "a1!"})
	void exception(String password) {
		try {
			new Password(password);
			throw new IllegalStateException();
		} catch (InvalidCustomerException exception) {
			assertThat(exception.getCode()).isEqualTo(ErrorCode.PASSWORD_FORMAT);
		}
	}
}
