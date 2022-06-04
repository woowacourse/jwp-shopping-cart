package woowacourse.auth.domain;

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Email {

	private static final String REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	private final String value;

	public Email(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		if (!Pattern.matches(REGEX, value)) {
			throw new IllegalArgumentException("이메일 형식에 맞지 않습니다.");
		}
	}
}
