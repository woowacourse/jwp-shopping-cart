package woowacourse.auth.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

	@Include @Getter
	private final Long id;
	private final Email email;
	private final Password password;
	private final Nickname nickname;

	public Customer(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = new Email(email);
		this.password = new Password(password);
		this.nickname = new Nickname(nickname);
	}

	public Customer(String email, String password, String nickname) {
		this(null, email, password, nickname);
	}

	public boolean isInvalidPassword(String password) {
		return !this.password.isSame(password);
	}

	public String getPassword() {
		return password.getValue();
	}

	public String getNickname() {
		return nickname.getValue();
	}

	public String getEmail() {
		return email.getValue();
	}
}
