package cart.domain.user;

import java.time.LocalDateTime;

public class User {

	private final Long id;
	private final String email;
	private final String password;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public User(final String email, final String password) {
		this(null, email, password, null, null);
	}

	public User(final Long id, final String email, final String password, final LocalDateTime createdAt,
		final LocalDateTime updatedAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
