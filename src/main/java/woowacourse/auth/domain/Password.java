package woowacourse.auth.domain;

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Password {

	private static final String regex = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}";

	private final String value;

	public Password(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		if (!Pattern.matches(regex, value)) {
			throw new IllegalArgumentException("비밀번호 형식에 맞지 않습니다.");
		}
	}
}
