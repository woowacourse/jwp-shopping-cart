package woowacourse.auth.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCustomerException;

@EqualsAndHashCode
@Getter
public class Nickname {

	private static final int MIN_LENGTH = 2;
	private static final int MAX_LENGTH = 10;

	private final String value;

	public Nickname(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
			throw new InvalidCustomerException(ErrorCode.NICKNAME_FORMAT, "닉네임은 2~10 길이어야 합니다.");
		}
	}
}
