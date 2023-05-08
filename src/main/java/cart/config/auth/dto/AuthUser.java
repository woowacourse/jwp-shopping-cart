package cart.config.auth.dto;

import cart.domain.user.User;

public class AuthUser {

	private Long id;
	private String email;
	private String password;

	public AuthUser() {
	}

	public AuthUser(final User user) {
		this(user.getId(), user.getEmail(), user.getPassword());
	}

	public AuthUser(final Long id, final String email, final String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
