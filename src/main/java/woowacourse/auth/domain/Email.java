package woowacourse.auth.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCustomerException;

@EqualsAndHashCode
@Getter
public class Email {

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
	);

	private final String value;

	public Email(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		Matcher matcher = EMAIL_PATTERN.matcher(value);
		if (!matcher.matches()) {
			throw new InvalidCustomerException(ErrorCode.EMAIL_FORMAT, "이메일 형식에 맞지 않습니다.");
		}
	}
}
