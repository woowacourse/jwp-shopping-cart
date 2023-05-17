package cart.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {
	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("이름이 null 또는 공백이면 예외가 발생한다")
	void nameNotNull(String name) {
		final String email = "email@email";
		final String password = "password";

		Assertions.assertThatThrownBy(() -> new Member(name, email, password))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이름은 공백이거나 비어있을 수 없습니다. 이름을 입력해주세요.");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("이메일이 null 또는 공백이면 예외가 발생한다")
	void emailNotNull(String email) {
		final String name = "name";
		final String password = "password";

		Assertions.assertThatThrownBy(() -> new Member(name, email, password))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이메일은 공백이거나 비어있을 수 없습니다. 이메일을 입력해주세요.");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("비밀번호가 null 또는 공백이면 예외가 발생한다")
	void passwordNotNull(String password) {
		final String name = "name";
		final String email = "email@email";

		Assertions.assertThatThrownBy(() -> new Member(name, email, password))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호는 공백이거나 비어있을 수 없습니다. 비밀번호를 입력해주세요.");
	}
}
