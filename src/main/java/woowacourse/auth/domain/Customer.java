package woowacourse.auth.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

	@Include @Getter
	private final Long id;
	private final Email email;
	private final EncryptedPassword password;
	private final Nickname nickname;

	private Customer(Long id, String email,
		String password, String nickname,
		EncryptionStrategy encryptionStrategy) {
		this.id = id;
		this.email = new Email(email);
		this.password = new EncryptedPassword(new Password(password), encryptionStrategy);
		this.nickname = new Nickname(nickname);
	}

	private Customer(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = new Email(email);
		this.password = new EncryptedPassword(password);
		this.nickname = new Nickname(nickname);
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

	public static CustomerBuilder builder() {
		return new CustomerBuilder();
	}

	public static class CustomerBuilder {
		private Long id;
		private String email;
		private String password;
		private String nickname;
		private EncryptionStrategy encryptionStrategy;

		public CustomerBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public CustomerBuilder email(String email) {
			this.email = email;
			return this;
		}

		public CustomerBuilder password(String password) {
			this.password = password;
			return this;
		}

		public CustomerBuilder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public CustomerBuilder encryptPassword(EncryptionStrategy encryptionStrategy) {
			this.encryptionStrategy = encryptionStrategy;
			return this;
		}

		public Customer build() {
			if (encryptionStrategy == null) {
				return new Customer(id, email, password, nickname);
			}
			return new Customer(id, email, password, nickname, encryptionStrategy);
		}
	}
}
